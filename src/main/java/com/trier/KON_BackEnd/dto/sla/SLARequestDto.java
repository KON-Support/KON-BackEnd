package com.trier.KON_BackEnd.dto.sla;

import com.trier.KON_BackEnd.enums.Prioridade;

import java.time.LocalDateTime;

public record SLARequestDto(
        Long cdSLA,
        Prioridade prioridade,
        LocalDateTime qtHorasResposta,
        LocalDateTime qtHorasResolucao
) {
}
