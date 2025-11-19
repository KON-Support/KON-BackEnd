package com.trier.KON_BackEnd.dto.request;

import com.trier.KON_BackEnd.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ChamadoRequestDTO(

        @NotBlank(message = "É preciso informar o título do chamado!")
        @Size(max = 30, message = "O máximo de caracteres é 30!")
        String dsTitulo,

        @NotBlank(message = "É preciso informar uma descrição para o chamado!")
        @Size(max = 300, message = "O máximo de caracteres é 300!")
        String dsDescricao,

        @NotNull(message = "É preciso informar o status do chamado!")
        Status status,

        @NotNull(message = "É preciso informar o código da categoria!")
        Long cdCategoria,

        // Opcional - pode ser atribuído depois (verificar com equipe)...
        Long cdUsuario,

        // Opcional - pode ser atribuído depois (verificar com equipe)...
        Long cdSLA

) {
}