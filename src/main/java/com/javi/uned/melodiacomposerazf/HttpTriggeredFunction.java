package com.javi.uned.melodiacomposerazf;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;

public class HttpTriggeredFunction {

    @FunctionName("melodia-composer-azf")
    public HttpResponseMessage run(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.FUNCTION
            ) HttpRequestMessage<Optional<String>> request, final ExecutionContext context) {

        context.getLogger().info("Java HTTP trigger processed a request.");

        String body = "";

        if (request.getBody().isPresent()) {
            body = request.getBody().get();
            context.getLogger().info("Request body: " + body);
        }

        // Build response
        HttpResponseMessage responseMessage = request
                .createResponseBuilder(HttpStatus.OK)
                .body("Hello, HTTP triggered function processed a request.\nBody: " + body)
                .build();

        return responseMessage;
    }

}
