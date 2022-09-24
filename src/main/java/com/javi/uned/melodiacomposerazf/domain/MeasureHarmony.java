package com.javi.uned.melodiacomposerazf.domain;

import com.javi.uned.melodiacore.model.Acorde;
import com.javi.uned.melodiacore.model.constants.Acordes;
import com.javi.uned.melodiacore.model.constants.Grado;

import java.util.Arrays;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

public class MeasureHarmony {

    private final PulseHarmony[] pulseHarmonies;

    public MeasureHarmony(PulseHarmony[] pulseHarmonies) {
        this.pulseHarmonies = pulseHarmonies;
    }

    public static MeasureHarmony random(RandomGenerator randomGenerator, int pulsos) {
        PulseHarmony[] pulseHarmonies = new PulseHarmony[pulsos];
        Grado[] grados = Grado.getGrados();
        Grado grado = grados[randomGenerator.nextInt(grados.length)];
        Acorde[] acordes = Acordes.getAcordes();
        Acorde acorde = acordes[randomGenerator.nextInt(acordes.length)];
        for(int i=0; i<pulsos; i++) {
            pulseHarmonies[i] = new PulseHarmony(grado, acorde);
        }
        return new MeasureHarmony(pulseHarmonies);
    }

    public PulseHarmony[] getPulseHarmonies() {
        return pulseHarmonies;
    }

    @Override
    public String toString() {
        String result = Arrays.stream(pulseHarmonies)
                .map(PulseHarmony::toString)
                .collect(Collectors.joining(", "));
        return "[ " + result + " ]";
    }
}
