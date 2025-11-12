package com.trier.KON_BackEnd.dto.request;

import com.trier.KON_BackEnd.enums.Prioridade;
import com.trier.KON_BackEnd.enums.Status;
import jakarta.persistence.Enumerated;
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

        @Enumerated
        Status status,

        @Enumerated
        Prioridade prioridade,

        @NotNull(message = "É preciso informar se o chamado está com o SLA violado ou não!")
        Boolean flSlaViolado

) {
}
