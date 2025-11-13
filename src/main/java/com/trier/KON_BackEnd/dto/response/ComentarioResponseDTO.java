package com.trier.KON_BackEnd.dto.response;

import com.trier.KON_BackEnd.model.ChamadoModel;
import com.trier.KON_BackEnd.model.UsuarioModel;

import java.time.LocalDate;
import java.time.LocalTime;

public record ComentarioResponseDTO(
        Long cdComentario,
        ChamadoModel cdChamado,
        UsuarioModel cdUsuarioModel,
        String dsConteudo,
        LocalDate dtCriacao,
        LocalTime hrCriacao
) {
}
