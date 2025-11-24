package com.trier.KON_BackEnd.dto.sla.response;

import com.trier.KON_BackEnd.enums.Prioridade;
import com.trier.KON_BackEnd.model.CategoriaModel;
import com.trier.KON_BackEnd.model.UsuarioModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record SLAResponseDto(
        Long cdSLA,
        String cdCategoria,
        String cdPlano,
        Integer qtHorasResposta,
        Integer qtHorasResolucao
) {
}
