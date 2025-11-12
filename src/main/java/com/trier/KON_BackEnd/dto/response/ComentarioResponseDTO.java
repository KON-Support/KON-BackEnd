package com.trier.KON_BackEnd.dto.response;

import java.time.LocalDateTime;

public record ComentarioResponseDTO(
        Long cdComentario,
        Chamado cdChamado,
        Usuario cdUsuario,
        String dsConteudo,
        LocalDateTime dtCriacao
) {
}
