package com.trier.KON_BackEnd.dto.response;

import com.trier.KON_BackEnd.model.ChamadoModel;
import com.trier.KON_BackEnd.model.Usuario;

import java.time.LocalDateTime;

public record ComentarioResponseDTO(
        Long cdComentario,
        ChamadoModel cdChamado,
        Usuario cdUsuario,
        String dsConteudo,
        LocalDateTime dtCriacao
) {
}
