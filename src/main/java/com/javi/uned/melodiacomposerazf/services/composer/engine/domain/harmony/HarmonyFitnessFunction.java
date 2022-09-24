package com.javi.uned.melodiacomposerazf.services.composer.engine.domain.harmony;

import com.javi.uned.melodiacomposerazf.domain.MeasureHarmony;
import com.javi.uned.melodiacomposerazf.domain.PulseHarmony;
import com.javi.uned.melodiacore.model.constants.Grado;
import io.jenetics.util.ISeq;
import org.apache.commons.math3.util.Pair;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class HarmonyFitnessFunction implements Function<ISeq<MeasureHarmonyGene>, Double> {

    @Override
    public Double apply(ISeq<MeasureHarmonyGene> seq) {

        List<MeasureHarmony> measures = seq.stream().map(MeasureHarmonyGene::allele).collect(Collectors.toList());
        List<PulseHarmony> pulses = measures.stream().flatMap(measure -> Arrays.stream(measure.getPulseHarmonies())).collect(Collectors.toList());
        Map<Grado, Long> gradeOccurrences = pulses.stream().collect(Collectors.groupingBy(PulseHarmony::getGrado, Collectors.counting()));

        Map<String, Pair<Integer, Double>> resultMap = new HashMap<>();
        resultMap.put("GRADE_DIVERSITY", new Pair<>(50, gradeDiversity(gradeOccurrences)));
        resultMap.put("GRADE_JUMPS", new Pair<>(50, gradeJumps(pulses)));
        resultMap.put("SINGLE_GRADE_MEASURES", new Pair<>(50, singleGradeMeasure(measures)));

        int totalWeight = resultMap.values().stream().mapToInt(Pair::getKey).sum();
        double result = resultMap.values().stream().mapToDouble(pair ->  pair.getValue() * ((double) pair.getKey() / totalWeight)).sum();

        resultMap.forEach((key, value) -> System.out.println(key + ": " + value.getValue()));
        System.out.println("Result: " + result);

        return result;
    }

    public double gradeDiversity(Map<Grado, Long> gradeOccurrences) {
        switch (gradeOccurrences.size()) {
            case 1:
            case 7:
                return 0.2;
            case 2:
            case 6:
                return 0.4;
            case 3:
            case 5:
                return 0.6;
            case 4:
                return 0.8;
            default:
                return 0;
        }
    }

    public double gradeJumps(List<PulseHarmony> pulses) {
        int jumps = 0;
        for (int i = 1; i < pulses.size(); i++) {
            if (!pulses.get(i-1).getGrado().equals(pulses.get(i).getGrado())) {
                jumps++;
            }
        }
        return (double) (pulses.size() - jumps) / pulses.size();
    }

    public double singleGradeMeasure(List<MeasureHarmony> measures) {
        int totalMeasures = measures.size();
        int singleGradeMeasures = measures.stream()
                .map(MeasureHarmony::getPulseHarmonies)
                .map(pulses -> Arrays.stream(pulses).map(PulseHarmony::getGrado).collect(Collectors.toSet()))
                .mapToInt(grados -> grados.size())
                .filter(size -> size == 1)
                .sum();
        return (double) singleGradeMeasures / totalMeasures;

    }

}
