package com.trier.KON_BackEnd.dto.request;

import com.trier.KON_BackEnd.model.ChamadoModel;
import com.trier.KON_BackEnd.model.UsuarioModel;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;


public record ComentarioRequestDTO(

        Long cdComentario,

        @NotNull(message = "O campo 'cdChamado' é obrigatório.")
        ChamadoModel cdChamado,

        @NotNull(message = "O campo 'cdUsuario' é obrigatório.")
        UsuarioModel cdUsuario,

        @Size(min = 2, max = 1000, message = "O campo 'dsConteudo' deve ter entre 2 e 1000 caracteres.")
        String dsConteudo,

        LocalDateTime dtCriacao
) {
}
