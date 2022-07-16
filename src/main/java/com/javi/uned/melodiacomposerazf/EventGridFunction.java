package com.javi.uned.melodiacomposerazf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javi.uned.melodiacomposerazf.domain.MelodiaContainers;
import com.javi.uned.melodiacomposerazf.domain.RequestEntity;
import com.javi.uned.melodiacomposerazf.domain.SheetDAO;
import com.javi.uned.melodiacomposerazf.domain.SheetEntity;
import com.javi.uned.melodiacomposerazf.exceptions.BlobStorageException;
import com.javi.uned.melodiacomposerazf.services.BlobStorageService;
import com.javi.uned.melodiacomposerazf.services.ComposerService;
import com.javi.uned.melodiacomposerazf.services.Event;
import com.javi.uned.melodiacore.exceptions.ExportException;
import com.javi.uned.melodiacore.io.export.MelodiaExporter;
import com.javi.uned.melodiacore.model.MelodiaScore;
import com.javi.uned.melodiacore.model.specs.ScoreSpecs;
import com.javi.uned.melodiacore.model.specs.ScoreSpecsDTO;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.EventGridTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class EventGridFunction {

    @FunctionName("composition-request")
    public String event(
            @EventGridTrigger(name = "specs") Event<String> requestEvent,
            final ExecutionContext executionContext
    ) {
        executionContext.getLogger().info("Java EventGrid trigger processed a request.");
        executionContext.getLogger().info("Request event: " + requestEvent);

        try {

            // Read score specs from event grid
            ObjectMapper objectMapper = new ObjectMapper();
            RequestEntity requestEntity = objectMapper.readValue(requestEvent.getData(), RequestEntity.class);
            ScoreSpecsDTO scoreSpecsDTO = objectMapper.readValue(requestEntity.getSpecs(), ScoreSpecsDTO.class);
            ScoreSpecs scoreSpecs = scoreSpecsDTO.toScoreSpecs();

            // Create sheet and insert into database
            SheetDAO sheetDAO = new SheetDAO();
            SheetEntity sheetEntity = new SheetEntity();
            sheetEntity.setDate(LocalDateTime.now().toString());
            sheetEntity.setName(scoreSpecs.getMovementTitle());
            sheetEntity.setOwnerId(scoreSpecs.getRequesterId());
            int sheetId = sheetDAO.insert(sheetEntity);

            // Compose score and save it to a file
            ComposerService composerService = new ComposerService();
            MelodiaScore melodiaScore = composerService.composeRandom();
            MelodiaExporter.toXML(melodiaScore, "generated.musicxml");

            // Upload score to blob storage
            String destinationPath = String.format("%s/%s.musicxml", sheetId, sheetId);
            BlobStorageService blobStorageService = new BlobStorageService(MelodiaContainers.SHEETS);
            blobStorageService.storeFile("generated.musicxml", destinationPath);

            return sheetEntity.toString();

        } catch (JsonProcessingException e) {
            executionContext.getLogger().severe("Error processing JSON: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (ExportException e) {
            executionContext.getLogger().severe("Error exporting to XML: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (BlobStorageException e) {
            executionContext.getLogger().severe("Error storing file: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (SQLException e) {
            executionContext.getLogger().severe("Error inserting sheet in database: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
