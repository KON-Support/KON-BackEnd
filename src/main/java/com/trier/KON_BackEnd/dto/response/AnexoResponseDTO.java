package com.trier.KON_BackEnd.dto.response;

import com.trier.KON_BackEnd.model.AnexoModel;

public record AnexoResponseDTO(
        Long cdAnexo,
        Long cdChamado,
        Long cdUsuario,
        String nmArquivo,
        String dsTipoArquivo
) {
    public AnexoResponseDTO(AnexoModel anexo) {
        this(
                anexo.getCdAnexo(),
                anexo.getCdChamado().getCdChamado(),
                anexo.getCdUsuario().getCdUsuario(),
                anexo.getNmArquivo(),
                anexo.getDsTipoArquivo()
        );
    }
}
