package com.javi.uned.melodiacomposerazf.domain;

import com.javi.uned.melodiacore.model.specs.ScoreSpecs;

public class SpecsDTOTransformer {

    public static ScoreSpecs toDomainObject(SpecsDTO specsDTO) {
        ScoreSpecs geneticSpecs = new ScoreSpecs();
        geneticSpecs.setRequesterId(specsDTO.getRequesterId());
        geneticSpecs.setMovementTitle(specsDTO.getMovementTitle());
        geneticSpecs.setMovementNumber(specsDTO.getMovementNumber());
        geneticSpecs.setAuthors(specsDTO.getAuthors());
        geneticSpecs.setMeasures(specsDTO.getMeasures());
        geneticSpecs.setCompas(specsDTO.getCompas());
        geneticSpecs.setInstrumentos(specsDTO.getInstrumentos());
        geneticSpecs.setTonalidad(TonalityDTOTransformer.toDomainObject(specsDTO.getTonalidad()));
        geneticSpecs.setPhraseLength(specsDTO.getPhraseLength());
        geneticSpecs.setMinFigura(specsDTO.getMinFigura());
        geneticSpecs.setMaxFigura(specsDTO.getMaxFigura());
        return geneticSpecs;
    }

    public static SpecsDTO toTransferObject(ScoreSpecs scoreSpecs) {
        SpecsDTO specsDTO = new SpecsDTO();
        specsDTO.setRequesterId(scoreSpecs.getRequesterId());
        specsDTO.setMovementTitle(scoreSpecs.getMovementTitle());
        specsDTO.setMovementNumber(scoreSpecs.getMovementNumber());
        specsDTO.setAuthors(scoreSpecs.getAuthors());
        specsDTO.setMeasures(scoreSpecs.getMeasures());
        specsDTO.setCompas(scoreSpecs.getCompas());
        specsDTO.setInstrumentos(scoreSpecs.getInstrumentos());
        specsDTO.setTonalidad(TonalityDTOTransformer.toTransferObject(scoreSpecs.getTonalidad()));
        specsDTO.setPhraseLength(scoreSpecs.getPhraseLength());
        specsDTO.setMinFigura(scoreSpecs.getMinFigura());
        specsDTO.setMaxFigura(scoreSpecs.getMaxFigura());
        return specsDTO;
    }

}
