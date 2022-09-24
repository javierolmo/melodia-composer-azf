package com.javi.uned.melodiacomposerazf.services.composer.engine.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GeneticSpecs {

    private int maximalPhenotypeAge = 70;
    private int populationSize = 50;
    private Optional<Integer> steadyFitness = Optional.empty();
    private List<String> alterers = new ArrayList<>();

    public int getMaximalPhenotypeAge() {
        return maximalPhenotypeAge;
    }

    public void setMaximalPhenotypeAge(int maximalPhenotypeAge) {
        this.maximalPhenotypeAge = maximalPhenotypeAge;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public Optional<Integer> getSteadyFitness() {
        return steadyFitness;
    }

    public void setSteadyFitness(Optional<Integer> steadyFitness) {
        this.steadyFitness = steadyFitness;
    }

    public List<String> getAlterers() {
        return alterers;
    }

    public void setAlterers(List<String> alterers) {
        this.alterers = alterers;
    }

}
