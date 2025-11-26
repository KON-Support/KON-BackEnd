package com.trier.KON_BackEnd.dto.response;

import com.trier.KON_BackEnd.model.ComentarioModel;
import java.time.LocalDate;
import java.time.LocalTime;

public record ComentarioResponseDTO(
        Long cdComentario,
        Long cdChamado,
        Long cdUsuario,
        String nmUsuario,
        String dsConteudo,
        LocalDate dtCriacao,
        LocalTime hrCriacao,
        Long cdAnexo
) {
    public ComentarioResponseDTO(ComentarioModel comentario) {
        this(
                comentario.getCdComentario(),
                comentario.getChamado().getCdChamado(),
                comentario.getUsuario().getCdUsuario(),
                comentario.getUsuario().getNmUsuario(),
                comentario.getDsConteudo(),
                comentario.getDtCriacao(),
                comentario.getHrCriacao(),
                comentario.getAnexo() != null ? comentario.getAnexo().getCdAnexo() : null
        );
    }
}
