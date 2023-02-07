package com.javi.uned.melodiacomposerazf;

import com.javi.uned.melodiacomposerazf.util.MelodiaRandom;
import com.javi.uned.melodiacore.domain.Figure;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Runner {

    public static void main(String[] args) {
        Figure[] figures = MelodiaRandom.measureRythm(64);
        String rythm = Arrays.stream(figures).map(figure -> figure.getType()).collect(Collectors.joining(" "));
        System.out.println(rythm);
    }

}
