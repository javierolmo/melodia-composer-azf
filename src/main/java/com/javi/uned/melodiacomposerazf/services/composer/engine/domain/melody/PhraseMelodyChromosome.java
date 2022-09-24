package com.javi.uned.melodiacomposerazf.services.composer.engine.domain.melody;

import com.javi.uned.melodiacomposerazf.domain.MeasureHarmony;
import com.javi.uned.melodiacomposerazf.domain.PhraseHarmony;
import com.javi.uned.melodiacore.model.MelodiaInstrument;
import com.javi.uned.melodiacore.model.measures.MelodiaMeasure;
import com.javi.uned.melodiacore.model.specs.ScoreSpecs;
import com.javi.uned.melodiacore.model.structure.Phrase;
import io.jenetics.Chromosome;
import io.jenetics.util.ISeq;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PhraseMelodyChromosome implements Chromosome<MeasureMelodyGene> {

    private final ISeq<MeasureMelodyGene> genes;
    private final ScoreSpecs scoreSpecs;
    private final int length;
    private final MelodiaInstrument melodiaInstrument;
    private final PhraseHarmony phraseHarmony;

    private PhraseMelodyChromosome(ISeq<MeasureMelodyGene> genes, ScoreSpecs scoreSpecs, int length, MelodiaInstrument melodiaInstrument, PhraseHarmony phraseHarmony) {
        this.genes = genes;
        this.scoreSpecs = scoreSpecs;
        this.length = length;
        this.melodiaInstrument = melodiaInstrument;
        this.phraseHarmony = phraseHarmony;
    }

    public static PhraseMelodyChromosome random(ScoreSpecs scoreSpecs, int length, PhraseHarmony phraseHarmony, MelodiaInstrument melodiaInstrument) {
        List<MeasureMelodyGene> measureMelodyGenes = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            MeasureHarmony measureHarmony = phraseHarmony.getMeasures().get(i);
            measureMelodyGenes.add(MeasureMelodyGene.random(melodiaInstrument, measureHarmony, scoreSpecs));
        }
        return new PhraseMelodyChromosome(ISeq.of(measureMelodyGenes), scoreSpecs, length, melodiaInstrument, phraseHarmony);
    }

    @Override
    public Chromosome<MeasureMelodyGene> newInstance(ISeq<MeasureMelodyGene> genes) {
        return new PhraseMelodyChromosome(genes, this.scoreSpecs, this.length, melodiaInstrument, phraseHarmony);
    }

    @Override
    public MeasureMelodyGene get(int index) {
        return genes.get(index);
    }

    @Override
    public int length() {
        return length;
    }

    @Override
    public Chromosome<MeasureMelodyGene> newInstance() {
        return random(scoreSpecs, length, phraseHarmony, melodiaInstrument);
    }

    public PhraseMelody toPhraseMelody() {
        List<MelodiaMeasure> measures = genes.stream().map(MeasureMelodyGene::allele).collect(Collectors.toList());
        return new PhraseMelody(measures);
    }

    public void print() {
        //TODO:
    }

    public ISeq<MeasureMelodyGene> getGenes() {
        return this.genes;
    }
}
