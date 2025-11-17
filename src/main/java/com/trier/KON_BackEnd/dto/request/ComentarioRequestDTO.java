package com.trier.KON_BackEnd.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ComentarioRequestDTO(

        @Schema(description = "Código do chamado")
        @NotNull(message = "O código do chamado é obrigatório.")
        Long cdChamado,

        @Schema(description = "Código do usuário")
        @NotNull(message = "O código do usuário é obrigatório.")
        Long cdUsuario,

        @Schema(description = "Conteúdo do comentário")
        @Size(min = 2, max = 1000, message = "O conteúdo deve ter entre 2 e 1000 caracteres.")
        String dsConteudo
) {
}
