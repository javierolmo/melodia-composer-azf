package com.javi.uned.melodiacomposerazf.util;

import com.javi.uned.melodiacomposerazf.services.composer.melody.MelodyMeasureGene;
import com.javi.uned.melodiacore.domain.Figure;
import com.javi.uned.melodiacore.domain.MelodiaToken;
import com.javi.uned.melodiacore.domain.TimeSignature;
import com.javi.uned.melodiacore.domain.enums.Step;
import io.jenetics.util.RandomRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MelodiaRandom {

    public static MelodiaToken[] tokenSequence(TimeSignature timeSignature) {
        Figure[] figures = MelodiaRandom.measureRythm(timeSignature.getDuration());
        return Arrays.stream(figures)
                .map(figure -> {
                    MelodiaToken token = new MelodiaToken();
                    token.setFigure(figure);
                    token.setStep(step());
                    return token;
                })
                .toArray(MelodiaToken[]::new);
    }

    public static Figure[] measureRythm(int duration) {
        List<Figure> figures = new ArrayList<>();
        while (duration > 0) {
            Figure figure = figure(Figure.SEMICORCHEA, Figure.BLANCA); //TODO: Esto no puede estar en código
            if(figure.getDuration() <= duration) {
                figures.add(figure);
                duration -= figure.getDuration();
            }
        }
        return figures.toArray(Figure[]::new);
    }

    public static Figure figure() {
        Figure[] figures = Figure.getFigures();
        return figures[RandomRegistry.random().nextInt(figures.length)];
    }

    public static Figure figure(Figure minFigure, Figure maxFigure) {
        Figure[] figures = Figure.getFigures();
        figures = Arrays.stream(figures)
                .filter(figure -> figure.getDuration() >= minFigure.getDuration())
                .filter(figure -> figure.getDuration() <= maxFigure.getDuration())
                .toArray(Figure[]::new);
        return figures[RandomRegistry.random().nextInt(figures.length)];
    }

    public static Step[] stepSequence(int size) {
        Step[] steps = new Step[size];
        for (int i = 0; i < size; i++) {
            steps[i] = step();
        }
        return steps;
    }

    public static Step step() {
        return Step.values()[RandomRegistry.random().nextInt(Step.values().length)];
    }
}
