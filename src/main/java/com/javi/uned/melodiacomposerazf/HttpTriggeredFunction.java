package com.javi.uned.melodiacomposerazf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javi.uned.melodiacomposerazf.domain.MelodiaContainers;
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

import java.io.IOException;
import java.util.Optional;

public class HttpTriggeredFunction {

    @FunctionName("compose")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req",methods = {HttpMethod.POST},authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context
    ) throws IOException, BlobStorageException {

        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse body to specs
        String body = request.getBody().orElseThrow(() -> new RuntimeException("No body"));
        ObjectMapper objectMapper = new ObjectMapper();
        ScoreSpecs specs = objectMapper.readValue(body, ScoreSpecs.class);

        // Insert sheet in database
        //SheetDAO sheetDAO = new SheetDAO();
        //SheetEntity sheet = new SheetEntity();
        //long sheetId = sheetDAO.insert(specs, context.getLogger());

        // Save specs in blob storage
        BlobStorageService blobStorageService = new BlobStorageService(MelodiaContainers.SHEETS);
        //String specsBlobName = blobStorageService.saveSpecs(specs);

        // Compose a random melodia
        ComposerService composerService = new ComposerService();
        MelodiaScore melodiaScore = composerService.composeRandom();
        try {
            MelodiaExporter.toXML(melodiaScore, "hola/score2.musicxml");
            blobStorageService.storeFile("hola/score2.musicxml", "test/score2.musicxml");
        } catch (ExportException e) {
            e.printStackTrace();
        }

        // Build response
        HttpResponseMessage responseMessage = request
                .createResponseBuilder(HttpStatus.OK)
                .body("Hello, HTTP triggered function processed a request.")
                .build();

        return responseMessage;
    }

    @FunctionName("env")
    public HttpResponseMessage env(
            @HttpTrigger(name = "req",methods = {HttpMethod.GET},authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context
    ) throws IOException, BlobStorageException {

        // Build response
        HttpResponseMessage responseMessage = request
                .createResponseBuilder(HttpStatus.OK)
                .body(System.getenv())
                .build();

        return responseMessage;
    }

    @FunctionName("short")
    public HttpResponseMessage runshort(
            @HttpTrigger(name = "req") HttpRequestMessage<Optional<String>> request,
            @DurableClientInput(name = "durableContext") DurableClientContext durableClientContext,
            final ExecutionContext context
    ) {
        DurableTaskClient client = durableClientContext.getClient();
        String instanceId = client.scheduleNewOrchestrationInstance("orchestrator");
        return durableClientContext.createCheckStatusResponse(request, instanceId);
    }

    @FunctionName("orchestrator")
    public String runorchestrator(@DurableOrchestrationTrigger(name = "runtimeState") String runtimeState) {
        return OrchestrationRunner.loadAndRun(runtimeState, ctx -> {
            return ctx.callActivity("SayHello", "Tokyo", String.class).await();
        });
    }

    @FunctionName("long")
    public String runlong(@DurableActivityTrigger(name = "name") String name) {
        for (int i = 0; i < 60; i++) {
            System.out.println(name);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return name;
    }


}
