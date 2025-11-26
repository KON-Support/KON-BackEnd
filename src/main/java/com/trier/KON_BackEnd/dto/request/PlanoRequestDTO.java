package com.trier.KON_BackEnd.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlanoRequestDTO(

        @NotBlank(message = "É necessario informar o nome do Plano!")
        String nmPlano,

        @NotNull(message = "É necessario informar o valor do Plano!")
        Double vlPlano,

        Integer limiteUsuarios,

        Integer hrRespostaPlano,
        Integer hrResolucaoPlano

) {
}
