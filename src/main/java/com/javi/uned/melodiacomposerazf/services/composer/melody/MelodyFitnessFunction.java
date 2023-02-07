package com.javi.uned.melodiacomposerazf.services.composer.melody;

import com.javi.uned.melodiacomposerazf.util.Pair;
import com.javi.uned.melodiacore.domain.MelodiaToken;
import com.javi.uned.melodiacore.domain.Phrase;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MelodyFitnessFunction implements Function<Phrase, Double> {

    @Override
    public Double apply(Phrase phrase) {
        MelodiaToken[] tokens = phrase.tokens();

        Map<String, Pair<Integer, Double>> resultMap = new HashMap<>();
        resultMap.put("AVOID_BIG_JUMPS", new Pair<>(100, avoidBigJumps(tokens)));
        resultMap.put("AVOID_DIRECTION_CHANGES", new Pair<>(50, avoidDirectionChanges(tokens)));

        int totalWeight = resultMap.values().stream().mapToInt(Pair::getLeft).sum();
        double result = resultMap.values().stream().mapToDouble(pair ->  pair.getRight() * ((double) pair.getLeft() / totalWeight)).sum();

        resultMap.forEach((key, value) -> System.out.println(key + ": " + value.getRight()));
        System.out.println("Result: " + result);

        return result;
    }

    public double avoidBigJumps(MelodiaToken[] tokens) {
        final int bigJumpCriteria = 5;
        int jumps = 0;
        int bigJumps = 0;
        for (int i = 1; i < tokens.length; i++) {
            int distance = Math.abs(tokens[i].pitch() - tokens[i-1].pitch());
            jumps++;
            if (distance >= bigJumpCriteria) bigJumps++;
        }
        return (double) (jumps - bigJumps) / jumps;
    }

    public double avoidDirectionChanges(MelodiaToken[] tokens) {
        int directionChanges = 0;
        for (int i = 2; i < tokens.length; i++) {
            boolean ascendentInterval1 = tokens[i-2].pitch() < tokens[i-1].pitch()? true: false;
            boolean ascendentInterval2 = tokens[i-1].pitch() < tokens[i].pitch()? true: false;
            if (ascendentInterval1 != ascendentInterval2) directionChanges++;
        }
        return (double) (tokens.length - directionChanges) / tokens.length;
    }

}
