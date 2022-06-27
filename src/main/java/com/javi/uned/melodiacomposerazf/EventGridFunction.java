package com.javi.uned.melodiacomposerazf;

import com.javi.uned.melodiacomposerazf.domain.SpecsDTO;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.EventGridTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;

public class EventGridFunction {

    @FunctionName("eventGrid")
    public void event(
            @EventGridTrigger(name = "specs") SpecsDTO specsDTO,
            final ExecutionContext executionContext
    ) {
        executionContext.getLogger().info("Java EventGrid trigger processed a request.");
        executionContext.getLogger().info("SpecsDTO: " + specsDTO.toString());
    }
}
