package com.javi.uned.melodiacomposerazf.services.composer;

import com.javi.uned.melodiacomposerazf.services.composer.melody.MelodyChromosome;
import com.javi.uned.melodiacomposerazf.services.composer.melody.MelodyMeasureGene;
import com.javi.uned.melodiacomposerazf.services.composer.melody.MelodyProblem;
import com.javi.uned.melodiacore.domain.TimeSignature;
import io.jenetics.Optimize;
import io.jenetics.engine.Engine;
import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import java.io.File;
import java.io.IOException;
import java.util.stream.Collectors;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;

public class Tester {

    public static void main(String[] args) {

        TimeSignature timeSignature = TimeSignature.COMPAS_4x4;
        Player player = new Player();

        MelodyProblem melodyProblem = new MelodyProblem(8, TimeSignature.COMPAS_4x4);

        MelodyChromosome melodyChromosome = (MelodyChromosome) Engine.builder(melodyProblem)
                .optimize(Optimize.MAXIMUM)
                .maximalPhenotypeAge(70)
                .populationSize(4)
                .build().stream()
                .limit(4000)
                .collect(toBestPhenotype())
                .genotype().chromosome();

        String staccato = melodyChromosome.staccato();
        Pattern pattern = new Pattern(staccato);
        pattern.setInstrument("VIOLIN");
        pattern.setTempo("lento");
        System.out.println(pattern);
        //player.play(staccato);

        try {
            File filePath = new File("/home/javi/Documentos/melodia/prueba.midi");
            filePath.getParentFile().mkdirs();
            MidiFileManager.savePatternToMidi(pattern, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
