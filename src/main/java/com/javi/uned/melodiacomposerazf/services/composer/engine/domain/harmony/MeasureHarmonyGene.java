package com.javi.uned.melodiacomposerazf.services.composer.engine.domain.harmony;

import com.javi.uned.melodiacomposerazf.domain.MeasureHarmony;
import com.javi.uned.melodiacore.model.Compas;
import com.javi.uned.melodiacore.model.Tonalidad;
import io.jenetics.Gene;
import io.jenetics.util.RandomRegistry;

import java.util.random.RandomGenerator;

public class MeasureHarmonyGene implements Gene<MeasureHarmony, MeasureHarmonyGene> {

    private final MeasureHarmony measureHarmony;
    private final Tonalidad tonalidad;
    private final Compas compas;

    private MeasureHarmonyGene(MeasureHarmony measureHarmony, Tonalidad tonalidad, Compas compas) {
        this.measureHarmony = measureHarmony;
        this.tonalidad = tonalidad;
        this.compas = compas;
    }

    @Override
    public MeasureHarmony allele() {
        return measureHarmony;
    }

    @Override
    public MeasureHarmonyGene newInstance() {
        return random(compas, tonalidad);
    }

    @Override
    public MeasureHarmonyGene newInstance(MeasureHarmony value) {
        return new MeasureHarmonyGene(measureHarmony, tonalidad, compas);
    }

    public static MeasureHarmonyGene random(Compas compas, Tonalidad tonalidad) {
        MeasureHarmony measureHarmony = MeasureHarmony.random(RandomRegistry.random(), compas.getPulsos());
        MeasureHarmonyGene measureHarmonyGene = new MeasureHarmonyGene(measureHarmony, tonalidad, compas);
        return measureHarmonyGene;
    }

    @Override
    public boolean isValid() {
        return true; //FIXME: Realmente es siempre válido?
    }

}
