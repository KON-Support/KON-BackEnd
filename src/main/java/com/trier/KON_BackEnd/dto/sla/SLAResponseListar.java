package com.trier.KON_BackEnd.dto.sla;

import com.trier.KON_BackEnd.model.CategoriaModel;
import com.trier.KON_BackEnd.model.UsuarioModel;

public record SLAResponseListar(
        Long cdSLA,
        String categoria,
        String plano,
        Integer qtHorasResposta,
        Integer qtHorasResolucao
) {
}
