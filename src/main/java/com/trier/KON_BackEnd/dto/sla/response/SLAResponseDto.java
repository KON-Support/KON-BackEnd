package com.trier.KON_BackEnd.dto.sla.response;

import com.trier.KON_BackEnd.enums.Prioridade;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record SLAResponseDto(
        Prioridade prioridade,
        String qtHorasResposta,
        String qtHorasResolucao
) {
}
