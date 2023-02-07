package com.javi.uned.melodiacomposerazf.services.composer.melody;

import com.javi.uned.melodiacore.domain.Measure;
import com.javi.uned.melodiacore.domain.Phrase;
import com.javi.uned.melodiacore.domain.TimeSignature;
import io.jenetics.Chromosome;
import io.jenetics.util.ISeq;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MelodyChromosome implements Chromosome<MelodyMeasureGene> {

    private MelodyMeasureGene[] genes;
    private int length;
    private TimeSignature timeSignature;

    public MelodyChromosome(MelodyMeasureGene[] genes, int length, TimeSignature timeSignature) {
        this.genes = genes;
        this.length = length;
        this.timeSignature = timeSignature;
    }

    @Override
    public Chromosome<MelodyMeasureGene> newInstance(ISeq<MelodyMeasureGene> genes) {
        MelodyChromosome melodyChromosome = new MelodyChromosome(genes.toArray(MelodyMeasureGene[]::new), this.length, timeSignature);
        return melodyChromosome;
    }

    @Override
    public MelodyMeasureGene get(int index) {
        return genes[index];
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public Chromosome<MelodyMeasureGene> newInstance() {
        return random(this.length, timeSignature);
    }

    public static MelodyChromosome random(int length, TimeSignature timeSignature) {
        MelodyMeasureGene[] genes = new MelodyMeasureGene[length];
        for (int i = 0; i < genes.length; i++) {
            genes[i] = MelodyMeasureGene.random(timeSignature);
        }

        return new MelodyChromosome(genes, length, timeSignature);
    }

    public String staccato() {
        return Arrays.stream(genes).map(gene -> gene.staccato()).collect(Collectors.joining(" | "));
    }

    public Phrase toPhrase() {
        Measure[] measures = Arrays.stream(genes)
                .map(MelodyMeasureGene::toMeasure)
                .toArray(Measure[]::new);
        return new Phrase(measures);
    }
}
