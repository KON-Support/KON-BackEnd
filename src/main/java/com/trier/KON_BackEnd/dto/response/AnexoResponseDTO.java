package com.trier.KON_BackEnd.dto.response;

import com.trier.KON_BackEnd.model.AnexoModel;

import java.time.LocalDate;
import java.time.LocalTime;

public record AnexoResponseDTO(
        Long cdAnexo,
        Long cdChamado,
        Long cdUsuario,
        String nmArquivo,
        String dsTipoArquivo,
        LocalDate dtUpload,
        LocalTime hrUpload
) {
    public AnexoResponseDTO(AnexoModel anexo) {
        this(
                anexo.getCdAnexo(),
                anexo.getChamado().getCdChamado(),
                anexo.getUsuario().getCdUsuario(),
                anexo.getNmArquivo(),
                anexo.getDsTipoArquivo(),
                anexo.getDtUpload(),
                anexo.getHrUpload()
        );
    }
}
