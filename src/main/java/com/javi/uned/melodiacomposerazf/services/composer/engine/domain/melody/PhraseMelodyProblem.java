package com.javi.uned.melodiacomposerazf.services.composer.engine.domain.melody;

import com.javi.uned.melodiacomposerazf.domain.PhraseHarmony;
import com.javi.uned.melodiacore.model.MelodiaInstrument;
import com.javi.uned.melodiacore.model.specs.ScoreSpecs;
import io.jenetics.Chromosome;
import io.jenetics.Genotype;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;

import java.util.function.Function;

public class PhraseMelodyProblem implements Problem<ISeq<MeasureMelodyGene>, MeasureMelodyGene, Double> {

    private ScoreSpecs scoreSpecs;
    private int length;
    private PhraseHarmony phraseHarmony;
    private MelodiaInstrument melodiaInstrument;

    public PhraseMelodyProblem(ScoreSpecs scoreSpecs, int length, PhraseHarmony phraseHarmony, MelodiaInstrument melodiaInstrument) {
        this.scoreSpecs = scoreSpecs;
        this.length = length;
        this.phraseHarmony = phraseHarmony;
        this.melodiaInstrument = melodiaInstrument;
    }

    @Override
    public Function<ISeq<MeasureMelodyGene>, Double> fitness() {
        return new MelodyFitnessFunction();
    }

    @Override
    public Codec<ISeq<MeasureMelodyGene>, MeasureMelodyGene> codec() {
        Chromosome<MeasureMelodyGene> firstChromosome = PhraseMelodyChromosome.random(scoreSpecs, length, phraseHarmony, melodiaInstrument);
        Genotype<MeasureMelodyGene> genotype = Genotype.of(firstChromosome);
        Function<Genotype<MeasureMelodyGene>, ISeq<MeasureMelodyGene>> function = gt ->
                ((PhraseMelodyChromosome)gt.chromosome()).getGenes();
        return Codec.of(genotype, function);
    }
}
