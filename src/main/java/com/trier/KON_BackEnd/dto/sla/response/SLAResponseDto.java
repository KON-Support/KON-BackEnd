package com.trier.KON_BackEnd.dto.sla.response;

import com.trier.KON_BackEnd.enums.Prioridade;

import java.time.LocalDateTime;

public record SLAResponseDto(
        Prioridade prioridade,
        LocalDateTime qtHorasResposta,
        LocalDateTime qtHorasResolucao
) {
}
