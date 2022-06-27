package com.javi.uned.melodiacomposerazf;

import com.javi.uned.melodiacomposerazf.domain.SpecsDTO;
import com.javi.uned.melodiacomposerazf.services.Event;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.EventGridTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;

public class EventGridFunction {

    @FunctionName("composition-request")
    public String event(
            @EventGridTrigger(name = "specs") Event<SpecsDTO> specsEvent,
            final ExecutionContext executionContext
    ) {
        executionContext.getLogger().info("Java EventGrid trigger processed a request.");
        executionContext.getLogger().info("Specs JSON: " + specsEvent);

        return specsEvent.getData().toString();
    }
}
