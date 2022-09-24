package com.javi.uned.melodiacomposerazf.services.composer.engine;

import io.jenetics.engine.EvolutionStatistics;

public class GeneticResult<T, C extends Comparable<? super C>> {

    private final T result;
    private final EvolutionStatistics<C, ?> statistics;

    public GeneticResult(T result, EvolutionStatistics statistics) {
        this.result = result;
        this.statistics = statistics;
    }

    public T getResult() {
        return result;
    }

    public EvolutionStatistics getStatistics() {
        return statistics;
    }
}
