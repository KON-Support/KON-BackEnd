package com.trier.KON_BackEnd.dto.response;

public record CategoriaResponseDTO(

        Long cdCategoria,
        String nmCategoria,
        Integer hrResposta,
        Integer hrResolucao,
        Boolean flAtivo
) {
}
