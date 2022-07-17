package com.javi.uned.melodiacomposerazf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javi.uned.melodiacomposerazf.domain.*;
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

import java.util.logging.Level;


public class EventGridFunction {

    @FunctionName("composition-request")
    public String event(
            @EventGridTrigger(name = "specs") Event<ScoreSpecsDTO> event,
            final ExecutionContext executionContext
    ) {
        try {
            executionContext.getLogger().info("Java EventGrid trigger processed a request.");
            executionContext.getLogger().info("Event: " + event);
            ScoreSpecsDTO scoreSpecsDTO = event.getData();
            executionContext.getLogger().info("ScoreSpecsDTO: " + scoreSpecsDTO);
            return "OK";
        } catch (Exception e) {
            executionContext.getLogger().log(Level.SEVERE, "Error processing event", e);
            return "ERROR";
        }


        /*
        try {

            // Read score specs from event grid
            executionContext.getLogger().info("Hasta aquí todo OK");
            ObjectMapper objectMapper = new ObjectMapper();
            ScoreSpecsDTO scoreSpecsDTO = objectMapper.readValue(request.getSpecs(), ScoreSpecsDTO.class);
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
        } catch (Exception e) {
            executionContext.getLogger().severe("Error: " + e.getStackTrace());
            throw e;
        }
        */
    }
}
