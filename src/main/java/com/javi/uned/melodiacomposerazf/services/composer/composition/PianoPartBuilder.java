package com.javi.uned.melodiacomposerazf.services.composer.composition;

import com.javi.uned.melodiacore.model.measures.MelodiaMeasure;
import com.javi.uned.melodiacore.model.specs.ScoreSpecs;

import java.util.List;

public class PianoPartBuilder extends PartBuilder {

    public PianoPartBuilder(ScoreSpecs scoreSpecs) {
        super(scoreSpecs);
    }

    @Override
    public List<MelodiaMeasure> buildPhrase() {
        return null;
    }

}
