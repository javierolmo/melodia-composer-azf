package com.javi.uned.melodiacomposerazf.domain;

import com.javi.uned.melodiacore.model.Tonalidad;
import com.javi.uned.melodiacore.model.constants.Tonalidades;

public class TonalityDTOTransformer {

    private TonalityDTOTransformer() {
    }

    public static Tonalidad toDomainObject(TonalityDTO tonalityDTO) {
        return Tonalidades.byId(tonalityDTO.getId());
    }

    public static TonalityDTO toTransferObject(Tonalidad tonalidad) {
        TonalityDTO tonalityDTO = new TonalityDTO();
        tonalityDTO.setId(tonalidad.getId());
        tonalityDTO.setMayor(tonalidad.isMayor());
        tonalityDTO.setAlteraciones(tonalidad.getAlteraciones());
        tonalityDTO.setAmericanName(tonalidad.getAmericanName());
        tonalityDTO.setTraditionalName(tonalidad.getTraditionalName());
        return tonalityDTO;
    }
}
