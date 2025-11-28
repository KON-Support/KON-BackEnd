package com.trier.KON_BackEnd.dto.sla;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record SLARequestDto(
        Long cdSLA,

        @NotNull(message = "Categoria é obrigatório")
        @Schema(description = "Categoria do sla", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Long cdCategoria,

        @NotNull(message = "Usuário é obrigatório")
        @Schema(description = "Usuário do sla", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Long cdPlano

) {
}
