package com.javi.uned.melodiacomposerazf;

import com.javi.uned.melodiacomposerazf.domain.MelodiaContainers;
import com.javi.uned.melodiacomposerazf.exceptions.BlobStorageException;
import com.javi.uned.melodiacomposerazf.services.BlobStorageService;
import com.javi.uned.melodiacomposerazf.services.ComposerService;
import com.javi.uned.melodiacore.exceptions.ExportException;
import com.javi.uned.melodiacore.io.export.MelodiaExporter;
import com.javi.uned.melodiacore.model.MelodiaScore;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class HttpTriggeredFunction {

    @FunctionName("melodia-composer-azf")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req",methods = {HttpMethod.GET},authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context
    ) throws IOException, BlobStorageException {

        context.getLogger().info("Java HTTP trigger processed a request.");

        // Save composition
        File file = new File("hola/score.musicxml");
        file.getParentFile().mkdirs();
        file.createNewFile();
        BlobStorageService blobStorageService = new BlobStorageService(MelodiaContainers.SHEETS);
        blobStorageService.storeFile("hola/score.musicxml", "test/score.musicxml");

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

}
