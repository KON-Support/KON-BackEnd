package com.trier.KON_BackEnd.dto.sla;

import com.trier.KON_BackEnd.enums.Prioridade;

import java.time.LocalDate;
import java.time.LocalTime;

public record SLARequestDto(
        Long cdSLA,
        Prioridade prioridade,
        LocalTime qtHorasResposta,
        LocalDate dsHorasResposta,
        LocalTime qtHorasResolucao,
        LocalDate dsHorasResolucao
) {
}
