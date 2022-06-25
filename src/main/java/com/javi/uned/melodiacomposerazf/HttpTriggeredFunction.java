package com.javi.uned.melodiacomposerazf;

import com.javi.uned.melodiacomposerazf.services.ComposerService;
import com.javi.uned.melodiacore.exceptions.ExportException;
import com.javi.uned.melodiacore.io.export.MelodiaExporter;
import com.javi.uned.melodiacore.model.MelodiaScore;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

public class HttpTriggeredFunction {

    @FunctionName("melodia-composer-azf")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req",methods = {HttpMethod.GET},authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context
    ) {

        context.getLogger().info("Java HTTP trigger processed a request.");

        String body = "";

        // Get all env vars
        for (String key : System.getenv().keySet()) {
            context.getLogger().info("env var: " + key + " = " + System.getenv(key));
        }

        // Get all system properties
        if (request.getBody().isPresent()) {
            body = request.getBody().get();
            context.getLogger().info("Request body: " + body);
        }

        // Compose a random melodia
        ComposerService composerService = new ComposerService();
        MelodiaScore melodiaScore = composerService.composeRandom();
        try {
            MelodiaExporter.toXML(melodiaScore, "hola/score.musicxml");
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

}
