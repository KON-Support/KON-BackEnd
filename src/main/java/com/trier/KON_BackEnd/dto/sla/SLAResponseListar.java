package com.trier.KON_BackEnd.dto.sla;

import com.trier.KON_BackEnd.model.CategoriaModel;
import com.trier.KON_BackEnd.model.UsuarioModel;

import java.time.LocalDateTime;

public record SLAResponseListar(
        Long cdSLA,
        String categoria,
        String usuario,
        LocalDateTime qtHorasResposta,
        LocalDateTime qtHorasResolucao
) {
}
