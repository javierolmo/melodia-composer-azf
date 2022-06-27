package com.javi.uned.melodiacomposerazf;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.EventGridTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;

public class EventGridFunction {

    @FunctionName("composition-request")
    public String event(
            @EventGridTrigger(name = "specs") String specsJson,
            final ExecutionContext executionContext
    ) {
        executionContext.getLogger().info("Java EventGrid trigger processed a request.");
        executionContext.getLogger().info("Specs JSON: " + specsJson);
        return specsJson;
    }
}
