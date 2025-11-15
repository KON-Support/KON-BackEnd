package com.trier.KON_BackEnd.dto.sla;

import com.trier.KON_BackEnd.model.CategoriaModel;
import com.trier.KON_BackEnd.model.UsuarioModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record SLAUpdateRequest(
        Long cdSLA,

        @NotNull
        @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        CategoriaModel categoria,

        @NotNull
        @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        UsuarioModel usuario,

        @NotNull
        @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Integer qtHorasResposta,

        @NotNull
        @Schema(example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Integer qtHorasResolucao
) {
}
