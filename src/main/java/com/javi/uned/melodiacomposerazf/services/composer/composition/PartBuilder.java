package com.javi.uned.melodiacomposerazf.services.composer.composition;

import com.javi.uned.melodiacore.model.MelodiaInstrument;
import com.javi.uned.melodiacore.model.constants.Instrumentos;
import com.javi.uned.melodiacore.model.measures.MelodiaMeasure;
import com.javi.uned.melodiacore.model.parts.MelodiaPart;
import com.javi.uned.melodiacore.model.specs.ScoreSpecs;
import com.javi.uned.melodiacore.model.structure.Structure;

import java.util.List;

public abstract class PartBuilder {

    private ScoreSpecs scoreSpecs;
    private MelodiaInstrument instrument;

    public PartBuilder(ScoreSpecs scoreSpecs) {
        this.scoreSpecs = scoreSpecs;
        this.instrument = Instrumentos.PIANO;
    }

    public MelodiaPart buildPart() {
        Structure structure = scoreSpecs.getStructure();
        MelodiaPart melodiaPart = new MelodiaPart(structure.measures(), instrument);

        // TODO:
        return melodiaPart;
    }

    public abstract List<MelodiaMeasure> buildPhrase();

}
