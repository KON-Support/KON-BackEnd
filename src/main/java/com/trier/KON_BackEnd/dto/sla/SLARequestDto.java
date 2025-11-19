package com.trier.KON_BackEnd.dto.sla;

import com.trier.KON_BackEnd.enums.Prioridade;
import com.trier.KON_BackEnd.model.CategoriaModel;
import com.trier.KON_BackEnd.model.UsuarioModel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record SLARequestDto(
        Long cdSLA,

        @NotNull(message = "Categoria é obrigatório")
        @Schema(description = "Categoria do sla", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Long cdCategoria,

        @NotNull(message = "Usaário é obrigatório")
        @Schema(description = "Usuário do sla", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Long cdPlano,

        @Schema(description = "Data de resposta", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Integer qtHorasResposta,

        @Schema(description = "Data de resolução", example = "3", requiredMode = Schema.RequiredMode.REQUIRED)
        Integer qtHorasResolucao
) {
}
