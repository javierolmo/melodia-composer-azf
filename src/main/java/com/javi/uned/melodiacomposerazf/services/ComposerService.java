package com.javi.uned.melodiacomposerazf.services;

import com.javi.uned.melodiacore.model.MelodiaScore;
import com.javi.uned.melodiacore.model.specs.ScoreSpecs;
import com.javi.uned.melodiacore.model.specs.ScoreSpecsBuilder;
import com.javi.uned.melodiacore.util.MelodiaRandom;

public class ComposerService {

    public MelodiaScore composeRandom() {
        MelodiaRandom melodiaRandom = new MelodiaRandom();

        ScoreSpecs scoreSpecs = ScoreSpecsBuilder.defaultBuilder().build();
        return melodiaRandom.randomScore(scoreSpecs);
    }

    public MelodiaScore compose(ScoreSpecs scoreSpecs) {
        MelodiaRandom melodiaRandom = new MelodiaRandom();
        return melodiaRandom.randomScore(scoreSpecs);
    }
}
