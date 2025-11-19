package com.trier.KON_BackEnd.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record AtribuirChamadoRequestDTO(

        @Schema(description = "Código do responsável (atendente)", example = "1")
        Long responsavel,

        @Schema(description = "Código da categoria", example = "1")
        Long cdCategoria,

        @Schema(description = "Código do SLA", example = "1")
        Long cdSLA

) {
}