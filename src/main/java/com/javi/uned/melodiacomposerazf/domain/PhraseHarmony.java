package com.javi.uned.melodiacomposerazf.domain;

import com.javi.uned.melodiacomposerazf.services.composer.engine.domain.harmony.PhraseHarmonyChromosome;

import java.util.ArrayList;
import java.util.List;

public class PhraseHarmony {

    private List<MeasureHarmony> measures;

    public PhraseHarmony(List<MeasureHarmony> measures) {
        this.measures = measures;
    }

    public PhraseHarmony() {
        this.measures = new ArrayList<>();
    }

    public List<MeasureHarmony> getMeasures() {
        return measures;
    }

    public void setMeasures(List<MeasureHarmony> measures) {
        this.measures = measures;
    }
}
