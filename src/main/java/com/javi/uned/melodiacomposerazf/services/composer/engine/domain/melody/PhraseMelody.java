package com.javi.uned.melodiacomposerazf.services.composer.engine.domain.melody;

import com.javi.uned.melodiacore.model.measures.MelodiaMeasure;

import java.util.List;

public class PhraseMelody {

    private List<MelodiaMeasure> measures;

    public PhraseMelody(List<MelodiaMeasure> measures) {
        this.measures = measures;
    }

    public List<MelodiaMeasure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<MelodiaMeasure> measures) {
        this.measures = measures;
    }
}
