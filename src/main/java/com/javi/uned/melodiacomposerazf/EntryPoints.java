package com.javi.uned.melodiacomposerazf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.javi.uned.melodiacomposerazf.domain.*;
import com.javi.uned.melodiacomposerazf.exceptions.BlobStorageException;
import com.javi.uned.melodiacomposerazf.services.BlobStorageService;
import com.javi.uned.melodiacomposerazf.services.composer.Composer;
import com.javi.uned.melodiacomposerazf.services.DatabaseService;
import com.javi.uned.melodiacomposerazf.services.Event;
import com.javi.uned.melodiacore.exceptions.ExportException;
import com.javi.uned.melodiacore.io.export.MelodiaExporter;
import com.javi.uned.melodiacore.model.MelodiaScore;
import com.javi.uned.melodiacore.model.specs.ScoreSpecs;
import com.javi.uned.melodiacore.model.specs.ScoreSpecsDTO;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.EventGridTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

public class EntryPoints {

    @FunctionName("composition-request")
    public String processCompositionRequest(
            @EventGridTrigger(name = "specs") Event<Request> event,
            final ExecutionContext executionContext
    ) {
        executionContext.getLogger().info("Java EventGrid trigger processed a request.");
        executionContext.getLogger().info("Event data: " + event);
        Request request = event.getData(Request.class);

        try {

            // Update request from database
            executionContext.getLogger().info("Updating request from database...");
            DatabaseService databaseService = new DatabaseService();
            request = databaseService.findRequestById(request.getId());
            ScoreSpecsDTO scoreSpecsDTO = request.scoreSpecsDTO();
            ScoreSpecs scoreSpecs = scoreSpecsDTO.toScoreSpecs();
            executionContext.getLogger().info("Request updated: " + request);
            executionContext.getLogger().info("Score specs: " + scoreSpecsDTO);

            // Get sheet object
            executionContext.getLogger().info("Getting sheet object...");
            SheetEntity sheetEntity;
            if (request.getSheetId() == null) {
                sheetEntity = databaseService.insertSheet(scoreSpecs.getMovementTitle(), scoreSpecs.getRequesterId());
            } else {
                sheetEntity = databaseService.findSheetById(request.getSheetId());
            }
            executionContext.getLogger().info("Sheet object: " + sheetEntity);

            // Compose score and save it to a file
            executionContext.getLogger().info("Composing score...");
            Composer composer = new Composer();
            MelodiaScore melodiaScore = composer.compose(scoreSpecs);
            String sourcePath = "scores/generated.musicxml";
            Files.deleteIfExists(new File(sourcePath).toPath());
            MelodiaExporter.toXML(melodiaScore, sourcePath);
            executionContext.getLogger().info("Score composed successfuly.");

            // Upload score to blob storage
            executionContext.getLogger().info("Uploading score to blob storage...");
            String destinationPath = String.format("%s/%s.musicxml", sheetEntity.getId(), sheetEntity.getId());
            BlobStorageService blobStorageService = new BlobStorageService(MelodiaContainers.SHEETS);
            blobStorageService.storeFile(sourcePath, destinationPath);
            Files.deleteIfExists(new File(sourcePath).toPath());
            executionContext.getLogger().info("Score uploaded successfuly.");

            return sheetEntity.toString();

        } catch (ExportException e) {
            executionContext.getLogger().severe("Error exporting to XML: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (BlobStorageException e) {
            executionContext.getLogger().severe("Error storing file: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (SQLException e) {
            executionContext.getLogger().severe("Error inserting sheet in database: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            executionContext.getLogger().severe("Error deserializing specs: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            executionContext.getLogger().severe("Error reading/writing file: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


}
