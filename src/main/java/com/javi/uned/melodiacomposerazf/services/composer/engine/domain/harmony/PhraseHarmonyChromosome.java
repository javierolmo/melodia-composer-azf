package com.javi.uned.melodiacomposerazf.services.composer.engine.domain.harmony;

import com.javi.uned.melodiacomposerazf.domain.MeasureHarmony;
import com.javi.uned.melodiacomposerazf.domain.PhraseHarmony;
import com.javi.uned.melodiacore.model.specs.ScoreSpecs;
import com.javi.uned.melodiacore.model.structure.Phrase;
import io.jenetics.Chromosome;
import io.jenetics.util.ISeq;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PhraseHarmonyChromosome implements Chromosome<MeasureHarmonyGene> {

    private final ISeq<MeasureHarmonyGene> genes;
    private final ScoreSpecs scoreSpecs;
    private final int length;

    private PhraseHarmonyChromosome(ISeq<MeasureHarmonyGene> genes, ScoreSpecs scoreSpecs, int length) {
        this.genes = genes;
        this.scoreSpecs = scoreSpecs;
        this.length = length;
    }

    @Override
    public Chromosome<MeasureHarmonyGene> newInstance(ISeq<MeasureHarmonyGene> genes) {
        return new PhraseHarmonyChromosome(genes, this.scoreSpecs, this.length);
    }

    @Override
    public MeasureHarmonyGene get(int index) {
        return genes.get(index);
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public Chromosome<MeasureHarmonyGene> newInstance() {
        return random(scoreSpecs, length);
    }

    public ISeq<MeasureHarmonyGene> getGenes() {
        return genes;
    }

    public static PhraseHarmonyChromosome random(ScoreSpecs scoreSpecs, int length) {
        List<MeasureHarmonyGene> auxGenes = new ArrayList<>();
        for(int i=0; i<length; i++) {
            auxGenes.add(MeasureHarmonyGene.random(scoreSpecs.getCompas(), scoreSpecs.getTonalidad()));
        }
        return new PhraseHarmonyChromosome(ISeq.of(auxGenes), scoreSpecs, length);
    }

    public PhraseHarmony toPhraseHarmony() {
        List<MeasureHarmony> measures = genes.stream().map(MeasureHarmonyGene::allele).collect(Collectors.toList());
        return new PhraseHarmony(measures);
    }

    public void print() {
        genes.stream().map(measureHarmonyGene -> measureHarmonyGene.allele()).forEach(System.out::println);
    }
}
