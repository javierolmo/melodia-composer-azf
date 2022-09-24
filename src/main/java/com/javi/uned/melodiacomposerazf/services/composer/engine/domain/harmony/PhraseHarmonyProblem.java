package com.javi.uned.melodiacomposerazf.services.composer.engine.domain.harmony;

import com.javi.uned.melodiacore.model.specs.ScoreSpecs;
import io.jenetics.Chromosome;
import io.jenetics.Genotype;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;
import java.util.function.Function;

public class PhraseHarmonyProblem implements Problem<ISeq<MeasureHarmonyGene>, MeasureHarmonyGene, Double> {

    private ScoreSpecs scoreSpecs;
    private int length;

    public PhraseHarmonyProblem(ScoreSpecs scoreSpecs, int length) {
        this.scoreSpecs = scoreSpecs;
        this.length = length;
    }

    @Override
    public Function<ISeq<MeasureHarmonyGene>, Double> fitness() {
        return new HarmonyFitnessFunction();
    }

    @Override
    public Codec<ISeq<MeasureHarmonyGene>, MeasureHarmonyGene> codec() {
        Chromosome<MeasureHarmonyGene> firstChromosome = PhraseHarmonyChromosome.random(scoreSpecs, length);
        Genotype<MeasureHarmonyGene> genotype = Genotype.of(firstChromosome);
        Function<Genotype<MeasureHarmonyGene>, ISeq<MeasureHarmonyGene>> function = gt ->
            ((PhraseHarmonyChromosome)gt.chromosome()).getGenes();
        return Codec.of(genotype, function);
    }
}
