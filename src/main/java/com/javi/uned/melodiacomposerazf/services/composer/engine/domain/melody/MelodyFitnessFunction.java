package com.javi.uned.melodiacomposerazf.services.composer.engine.domain.melody;

import io.jenetics.util.ISeq;

import java.util.function.Function;

public class MelodyFitnessFunction implements Function<ISeq<MeasureMelodyGene>, Double> {

    @Override
    public Double apply(ISeq<MeasureMelodyGene> measureMelodyGenes) {
        //TODO:
        return 1D;
    }

}
