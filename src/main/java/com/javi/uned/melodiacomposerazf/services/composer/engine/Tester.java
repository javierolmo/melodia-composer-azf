package com.javi.uned.melodiacomposerazf.services.composer.engine;

import com.javi.uned.melodiacomposerazf.services.composer.engine.domain.GeneticSpecs;
import com.javi.uned.melodiacomposerazf.services.composer.engine.domain.harmony.PhraseHarmonyChromosome;
import com.javi.uned.melodiacomposerazf.services.composer.engine.domain.harmony.PhraseHarmonyProblem;
import com.javi.uned.melodiacomposerazf.services.composer.engine.domain.melody.PhraseMelodyChromosome;
import com.javi.uned.melodiacomposerazf.services.composer.engine.domain.melody.PhraseMelodyProblem;
import com.javi.uned.melodiacore.model.constants.Instrumentos;
import com.javi.uned.melodiacore.model.specs.ScoreSpecs;
import com.javi.uned.melodiacore.model.specs.ScoreSpecsBuilder;
import com.javi.uned.melodiacore.model.structure.Phrase;

import java.util.Optional;

public class Tester {

    public static void main(String[] args) {

        ScoreSpecs scoreSpecs = ScoreSpecsBuilder.defaultBuilder().build();
        Phrase phrase = new Phrase();
        phrase.setLength(12);

        GeneticSpecs geneticSpecs = new GeneticSpecs();
        geneticSpecs.setMaximalPhenotypeAge(70);
        geneticSpecs.setPopulationSize(40000);
        geneticSpecs.setSteadyFitness(Optional.of(50));

        PhraseHarmonyProblem phraseHarmonyProblem = new PhraseHarmonyProblem(scoreSpecs, phrase);
        CustomEngine customEngine1 = new CustomEngine(phraseHarmonyProblem, geneticSpecs);
        GeneticResult<PhraseHarmonyChromosome, Double> harmonyResult = customEngine1.execute();
        System.out.println(harmonyResult.getStatistics().toString());
        harmonyResult.getResult().print();

        PhraseMelodyProblem phraseMelodyProblem = new PhraseMelodyProblem(scoreSpecs, phrase, harmonyResult.getResult().toPhraseHarmony(), Instrumentos.PIANO);
        CustomEngine customEngine2 = new CustomEngine(phraseMelodyProblem, geneticSpecs);
        GeneticResult<PhraseMelodyChromosome, Double> melodyResult = customEngine2.execute();
        System.out.println(melodyResult.getStatistics().toString());
        melodyResult.getResult().print();

    }

}
