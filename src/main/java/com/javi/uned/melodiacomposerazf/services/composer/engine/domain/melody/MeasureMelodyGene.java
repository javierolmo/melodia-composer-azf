package com.javi.uned.melodiacomposerazf.services.composer.engine.domain.melody;

import com.javi.uned.melodiacomposerazf.domain.MeasureHarmony;
import com.javi.uned.melodiacomposerazf.services.composer.engine.MelodiaRandom;
import com.javi.uned.melodiacore.model.Figura;
import com.javi.uned.melodiacore.model.MelodiaInstrument;
import com.javi.uned.melodiacore.model.measures.MelodiaMeasure;
import com.javi.uned.melodiacore.model.specs.ScoreSpecs;
import io.jenetics.Gene;
import io.jenetics.util.RandomRegistry;

import java.util.random.RandomGenerator;

public class MeasureMelodyGene implements Gene<MelodiaMeasure, MeasureMelodyGene> {

    private final MelodiaMeasure melodiaMeasure;
    private final MeasureHarmony harmony;
    private final ScoreSpecs scoreSpecs;
    private final MelodiaInstrument melodiaInstrument;

    private MeasureMelodyGene(MelodiaMeasure melodiaMeasure, MeasureHarmony harmony, ScoreSpecs scoreSpecs, MelodiaInstrument melodiaInstrument) {
        this.melodiaMeasure = melodiaMeasure;
        this.harmony = harmony;
        this.scoreSpecs = scoreSpecs;
        this.melodiaInstrument = melodiaInstrument;
    }

    public static MeasureMelodyGene random(MelodiaInstrument melodiaInstrument, MeasureHarmony measureHarmony, ScoreSpecs scoreSpecs) {
        MelodiaRandom melodiaRandom = new MelodiaRandom(RandomRegistry.random());
        MelodiaMeasure melodiaMeasure = melodiaRandom.randomMeasure(melodiaInstrument, scoreSpecs.getMinFigura(), scoreSpecs.getMaxFigura());
        return new MeasureMelodyGene(melodiaMeasure, measureHarmony, scoreSpecs, melodiaInstrument);
    }

    @Override
    public MelodiaMeasure allele() {
        return melodiaMeasure;
    }

    @Override
    public MeasureMelodyGene newInstance() {
        MelodiaRandom melodiaRandom = new MelodiaRandom(RandomRegistry.random());
        MelodiaMeasure melodiaMeasure = melodiaRandom.randomMeasure(melodiaInstrument, scoreSpecs.getMinFigura(), scoreSpecs.getMaxFigura());
        MeasureMelodyGene measureMelodyGene = new MeasureMelodyGene(melodiaMeasure, harmony, scoreSpecs, melodiaInstrument);
        return measureMelodyGene;

    }

    @Override
    public MeasureMelodyGene newInstance(MelodiaMeasure melodiaMeasure) {
        return new MeasureMelodyGene(melodiaMeasure, harmony, scoreSpecs, melodiaInstrument);
    }

    @Override
    public boolean isValid() {
        return true; //TODO:
    }


}
