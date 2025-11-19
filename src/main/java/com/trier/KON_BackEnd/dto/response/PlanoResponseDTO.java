package com.trier.KON_BackEnd.dto.response;

public record PlanoResponseDTO(

        Long cdPlano,

        String nmPlano,

        Double vlPlano,

        Integer limiteUsuarios,

        Integer hrRespostaPlano,

        Integer hrResolucaoPlano
) {
}
