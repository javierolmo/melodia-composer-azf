package com.javi.uned.melodiacomposerazf;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javi.uned.melodiacomposerazf.domain.MelodiaContainers;
import com.javi.uned.melodiacomposerazf.domain.SpecsDTO;
import com.javi.uned.melodiacomposerazf.domain.SpecsDTOTransformer;
import com.javi.uned.melodiacomposerazf.exceptions.BlobStorageException;
import com.javi.uned.melodiacomposerazf.services.BlobStorageService;
import com.javi.uned.melodiacomposerazf.services.ComposerService;
import com.javi.uned.melodiacore.exceptions.ExportException;
import com.javi.uned.melodiacore.io.export.MelodiaExporter;
import com.javi.uned.melodiacore.model.MelodiaScore;
import com.javi.uned.melodiacore.model.specs.ScoreSpecs;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.microsoft.durabletask.DurableTaskClient;
import com.microsoft.durabletask.OrchestrationRunner;
import com.microsoft.durabletask.azurefunctions.DurableActivityTrigger;
import com.microsoft.durabletask.azurefunctions.DurableClientContext;
import com.microsoft.durabletask.azurefunctions.DurableClientInput;
import com.microsoft.durabletask.azurefunctions.DurableOrchestrationTrigger;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class HttpTriggeredFunction {

    @FunctionName("composition-request")
    public HttpResponseMessage runshort(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}) HttpRequestMessage<Optional<String>> req,
            @DurableClientInput(name = "durableContext") DurableClientContext durableClientContext,
            final ExecutionContext context
    ) {
        try {

            context.getLogger().info("Java HTTP trigger processed a request.");

            // Parse body to specs
            context.getLogger().info("Parsing body to specs");
            String body = req.getBody().orElseThrow(() -> new RuntimeException("No body"));
            ObjectMapper objectMapper = new ObjectMapper();
            SpecsDTO specsDto = objectMapper.readValue(body, SpecsDTO.class);
            ScoreSpecs specs = SpecsDTOTransformer.toDomainObject(specsDto);
            context.getLogger().info("Score specs parsed successfully: " + specsDto);

            // Insert sheet in database
            //SheetDAO sheetDAO = new SheetDAO();
            //SheetEntity sheet = new SheetEntity();
            //long sheetId = sheetDAO.insert(specs, context.getLogger());

            // Save specs in blob storage
            context.getLogger().info("Saving specs in blob storage");
            BlobStorageService blobStorageService = new BlobStorageService(MelodiaContainers.SHEETS);
            File file = new File("specs.json");
            objectMapper.writeValue(file, specs);
            blobStorageService.storeFile(file.getAbsolutePath(), String.format("specs/%s.json", specs.getRequesterId()));
            context.getLogger().info("Specs saved in blob storage");

            // Successful response
            DurableTaskClient client = durableClientContext.getClient();
            String instanceId = client.scheduleNewOrchestrationInstance("orchestrator");
            return durableClientContext.createCheckStatusResponse(req, instanceId);

        } catch (JsonProcessingException e) {
            return req.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Invalid JSON").build();
        } catch (Exception e) {
            return req.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error").build();
        }

    }

    @FunctionName("orchestrator")
    public String runorchestrator(@DurableOrchestrationTrigger(name = "runtimeState") String runtimeState) {
        return OrchestrationRunner.loadAndRun(runtimeState, ctx -> {
            return ctx.callActivity("long", "Tokyo", String.class).await();
        });
    }

    @FunctionName("long")
    public String runlong(@DurableActivityTrigger(name = "name") String name) throws BlobStorageException {

        // Create blob storage service
        BlobStorageService blobStorageService = new BlobStorageService(MelodiaContainers.SHEETS);

        // Compose a random melodia
        ComposerService composerService = new ComposerService();
        MelodiaScore melodiaScore = composerService.composeRandom();
        try {
            MelodiaExporter.toXML(melodiaScore, "hola/score2.musicxml");
            blobStorageService.storeFile("hola/score2.musicxml", "test/score2.musicxml");
        } catch (ExportException e) {
            e.printStackTrace();
        }

        return name;
    }


}
