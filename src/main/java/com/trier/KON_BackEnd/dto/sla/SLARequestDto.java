package com.trier.KON_BackEnd.dto.sla;

import com.trier.KON_BackEnd.enums.Prioridade;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record SLARequestDto(
        Long cdSLA,

        @NotNull(message = "Prioridade é obrigatório")
        @Schema(description = "Prioridade do sla", example = "ALTA", requiredMode = Schema.RequiredMode.REQUIRED)
        Prioridade prioridade,

        @Schema(description = "Data de resposta", example = "13/11/2025 11:30:00.000", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime qtHorasResposta,

        @Schema(description = "Data de resolução", example = "13/11/2025 13:30:00.000", requiredMode = Schema.RequiredMode.REQUIRED)
        LocalDateTime qtHorasResolucao
) {
}
