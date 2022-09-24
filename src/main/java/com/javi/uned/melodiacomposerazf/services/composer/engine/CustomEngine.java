package com.javi.uned.melodiacomposerazf.services.composer.engine;

import com.javi.uned.melodiacomposerazf.services.composer.engine.domain.GeneticSpecs;
import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;
import io.jenetics.engine.EvolutionStream;
import io.jenetics.engine.Problem;
import io.jenetics.util.ISeq;
import java.util.List;
import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import static io.jenetics.engine.Limits.bySteadyFitness;

public class CustomEngine<T, G extends Gene<?, G>, C extends Comparable<? super C>>{

    private Problem<T, G, C> problem;
    private GeneticSpecs specs;
    private Engine<G, C> engine;

    public CustomEngine(Problem<T, G, C> problem, GeneticSpecs specs) {
        this.problem = problem;
        this.specs = specs;
        this.engine = buildEngine(specs, problem);
    }

    public GeneticResult<T, C> execute() {
        EvolutionStatistics<C, ?> statistics = EvolutionStatistics.ofComparable();
        EvolutionStream<G, C> evolutionStream = engine.stream();
        if(specs.getSteadyFitness().isPresent()) evolutionStream = evolutionStream.limit(bySteadyFitness(specs.getSteadyFitness().get()));
        Phenotype<G, C> best = evolutionStream
                .limit(4000) // TODO: identificar qué es este 4000 y añadirlo a las especificaciones
                .peek(statistics)
                .collect(toBestPhenotype());

        List<G> genes = best.genotype().chromosome().stream().toList();
        GeneticResult<T, C> geneticResult = new GeneticResult<>((T) ISeq.of(genes), statistics);
        return geneticResult;
    }

    private Engine<G, C> buildEngine(GeneticSpecs geneticSpecs, Problem<T, G, C> problem) {
        return Engine.builder(problem)
                .optimize(Optimize.MAXIMUM)
                .maximalPhenotypeAge(geneticSpecs.getMaximalPhenotypeAge())
                .populationSize(geneticSpecs.getPopulationSize())
                .alterers(
                        new SinglePointCrossover<>(0.0),
                        new SwapMutator<>(0.0)
                )
                .build();
    }


}
