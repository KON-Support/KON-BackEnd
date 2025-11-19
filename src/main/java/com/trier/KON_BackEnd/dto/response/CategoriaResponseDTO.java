package com.trier.KON_BackEnd.dto.response;

import java.time.LocalDateTime;

public record CategoriaResponseDTO(

        Long cdCategoria,
        String nmCategoria,
        Integer hrResposta,
        Integer hrResolucao,
        Boolean flAtivo
) {
}
