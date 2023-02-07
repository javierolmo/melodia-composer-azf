package com.javi.uned.melodiacomposerazf.services.composer.melody;

import com.javi.uned.melodiacomposerazf.util.MelodiaRandom;
import com.javi.uned.melodiacore.domain.Measure;
import com.javi.uned.melodiacore.domain.MelodiaToken;
import com.javi.uned.melodiacore.domain.TimeSignature;
import io.jenetics.Gene;

import java.util.Arrays;
import java.util.stream.Collectors;

public class MelodyMeasureGene implements Gene<MelodiaToken[], MelodyMeasureGene> {

    private MelodiaToken[] tokens;
    private TimeSignature timeSignature;

    @Override
    public MelodiaToken[] allele() {
        return tokens;
    }

    @Override
    public MelodyMeasureGene newInstance() {
        return random(this.timeSignature);
    }

    @Override
    public MelodyMeasureGene newInstance(MelodiaToken[] tokens) {
        MelodyMeasureGene melodyMeasureGene = new MelodyMeasureGene();
        melodyMeasureGene.tokens = tokens;
        melodyMeasureGene.timeSignature = timeSignature;
        return melodyMeasureGene;
    }

    @Override
    public boolean isValid() {
        return true; //TODO:
    }

    public static MelodyMeasureGene random(TimeSignature timeSignature) {
        MelodyMeasureGene melodyMeasureGene = new MelodyMeasureGene();
        melodyMeasureGene.timeSignature = timeSignature;
        melodyMeasureGene.tokens = MelodiaRandom.tokenSequence(timeSignature);
        return melodyMeasureGene;
    }

    public String staccato() {
        return Arrays.stream(tokens).map(token -> token.toStaccatoString()).collect(Collectors.joining(" "));
    }

    public Measure toMeasure() {
        return new Measure(tokens);
    }
}
