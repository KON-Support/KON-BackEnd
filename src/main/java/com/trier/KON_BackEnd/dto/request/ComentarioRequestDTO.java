package com.trier.KON_BackEnd.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ComentarioRequestDTO(

        @NotNull(message = "O código do chamado é obrigatório.")
        Long cdChamado,

        @NotNull(message = "O código do usuário é obrigatório.")
        Long cdUsuario,

        @Size(min = 2, max = 1000, message = "O conteúdo deve ter entre 2 e 1000 caracteres.")
        String dsConteudo
) {
}
