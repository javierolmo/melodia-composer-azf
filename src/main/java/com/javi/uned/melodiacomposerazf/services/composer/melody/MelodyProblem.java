package com.javi.uned.melodiacomposerazf.services.composer.melody;

import com.javi.uned.melodiacore.domain.Phrase;
import com.javi.uned.melodiacore.domain.TimeSignature;
import io.jenetics.Chromosome;
import io.jenetics.Genotype;
import io.jenetics.engine.Codec;
import io.jenetics.engine.Problem;
import java.util.function.Function;

public class MelodyProblem implements Problem<Phrase, MelodyMeasureGene, Double> {

    private int length;
    private TimeSignature timeSignature;

    public MelodyProblem(int length, TimeSignature timeSignature) {
        this.length = length;
        this.timeSignature = timeSignature;
    }

    @Override
    public Function<Phrase, Double> fitness() {
        return new MelodyFitnessFunction();
    }

    @Override
    public Codec<Phrase, MelodyMeasureGene> codec() {
        Chromosome<MelodyMeasureGene> firstChromosome = MelodyChromosome.random(8, timeSignature);
        Genotype<MelodyMeasureGene> genotype = Genotype.of(firstChromosome);
        Function<Genotype<MelodyMeasureGene>, Phrase> function = gt -> ((MelodyChromosome)gt.chromosome()).toPhrase();
        return Codec.of(genotype, function);
    }
}
