package com.trier.KON_BackEnd.dto.sla.response;

public record SLAResponseDto(
        Long cdSLA,
        String cdCategoria,
        String cdPlano,
        Integer qtHorasResposta,
        Integer qtHorasResolucao
) {
}
