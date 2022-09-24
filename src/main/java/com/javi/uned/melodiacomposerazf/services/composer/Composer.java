package com.javi.uned.melodiacomposerazf.services.composer;

import com.javi.uned.melodiacomposerazf.services.composer.composition.PartBuilder;
import com.javi.uned.melodiacomposerazf.services.composer.composition.PianoPartBuilder;
import com.javi.uned.melodiacomposerazf.services.composer.engine.CustomEngine;
import com.javi.uned.melodiacomposerazf.services.composer.engine.GeneticResult;
import com.javi.uned.melodiacomposerazf.services.composer.engine.domain.GeneticSpecs;
import com.javi.uned.melodiacomposerazf.services.composer.engine.domain.harmony.PhraseHarmonyChromosome;
import com.javi.uned.melodiacomposerazf.services.composer.engine.domain.harmony.PhraseHarmonyProblem;
import com.javi.uned.melodiacomposerazf.services.composer.engine.domain.melody.PhraseMelodyChromosome;
import com.javi.uned.melodiacomposerazf.services.composer.engine.domain.melody.PhraseMelodyProblem;
import com.javi.uned.melodiacore.model.MelodiaInstrument;
import com.javi.uned.melodiacore.model.MelodiaScore;
import com.javi.uned.melodiacore.model.constants.Instrumentos;
import com.javi.uned.melodiacore.model.measures.MelodiaMeasure;
import com.javi.uned.melodiacore.model.parts.MelodiaPart;
import com.javi.uned.melodiacore.model.specs.ScoreSpecs;
import java.util.ArrayList;
import java.util.List;

public class Composer {

    public MelodiaScore compose(ScoreSpecs scoreSpecs) {
        List<MelodiaPart> parts = new ArrayList<>();
        for (MelodiaInstrument instrumento : scoreSpecs.getMelodiaInstruments()) {
            PartBuilder partBuilder = new PianoPartBuilder(scoreSpecs);
            MelodiaPart melodiaPart = partBuilder.buildPart();
            parts.add(melodiaPart);
        }
        return new MelodiaScore(parts, scoreSpecs.getMovementTitle(), scoreSpecs.getMovementNumber(), scoreSpecs.getAuthors());
    }

    public List<MelodiaMeasure> phrase(ScoreSpecs scoreSpecs, GeneticSpecs geneticSpecs, int length) {

        PhraseHarmonyProblem phraseHarmonyProblem = new PhraseHarmonyProblem(scoreSpecs, length);
        CustomEngine customEngine1 = new CustomEngine(phraseHarmonyProblem, geneticSpecs);
        GeneticResult<PhraseHarmonyChromosome, Double> harmonyResult = customEngine1.execute();

        PhraseMelodyProblem phraseMelodyProblem = new PhraseMelodyProblem(scoreSpecs, length, harmonyResult.getResult().toPhraseHarmony(), Instrumentos.PIANO);  //TODO: Iterar por instrumento en lugar de esto
        CustomEngine customEngine2 = new CustomEngine(phraseMelodyProblem, geneticSpecs);
        GeneticResult<PhraseMelodyChromosome, Double> melodyResult = customEngine2.execute();

        return melodyResult.getResult().toPhraseMelody().getMeasures();
    }

}
