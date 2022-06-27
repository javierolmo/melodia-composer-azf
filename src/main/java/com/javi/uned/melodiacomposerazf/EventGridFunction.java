package com.javi.uned.melodiacomposerazf;

import com.azure.messaging.eventgrid.EventGridEvent;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.EventGridTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;

public class EventGridFunction {

    @FunctionName("eventGrid")
    public String event(
            @EventGridTrigger(name = "specs") EventGridEvent event,
            final ExecutionContext executionContext
    ) {
        executionContext.getLogger().info("Java EventGrid trigger processed a request.");
        executionContext.getLogger().info("EventGridEvent: " + event.getSubject());
        return "Hello, World!";
    }
}
