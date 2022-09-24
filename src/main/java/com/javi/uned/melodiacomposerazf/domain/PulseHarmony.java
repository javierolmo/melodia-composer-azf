package com.javi.uned.melodiacomposerazf.domain;

import com.javi.uned.melodiacore.model.Acorde;
import com.javi.uned.melodiacore.model.constants.Acordes;
import com.javi.uned.melodiacore.model.constants.Grado;

import java.util.random.RandomGenerator;

public class PulseHarmony {

    private final Grado grado;
    private final Acorde acorde;

    protected PulseHarmony(RandomGenerator randomGenerator) {
        Grado[] grados = Grado.getGrados();
        grado = grados[randomGenerator.nextInt(grados.length)];
        Acorde[] acordes = Acordes.getAcordes();
        acorde = acordes[randomGenerator.nextInt(acordes.length)];
    }

    public PulseHarmony(Grado grado, Acorde acorde) {
        this.grado = grado;
        this.acorde = acorde;
    }

    public Grado getGrado() {
        return grado;
    }

    public Acorde getAcorde() {
        return acorde;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", grado, acorde);
    }
}
