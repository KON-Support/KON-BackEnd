package com.trier.KON_BackEnd.dto.response;

import com.trier.KON_BackEnd.enums.Status;
import com.trier.KON_BackEnd.model.AnexoModel;
import com.trier.KON_BackEnd.model.CategoriaModel;
import com.trier.KON_BackEnd.model.UsuarioModel;

import java.time.LocalDate;
import java.time.LocalTime;

public record ChamadoResponseDTO(

        Long cdChamado,
        String dsTitulo,
        String dsDescricao,
        Status status,
        UsuarioModel nmUsuario,
        AnexoModel anexo,
        CategoriaModel nmCategoria,
        LocalDate dtCriacao,
        LocalTime hrCriacao,
        LocalDate dtFechamento,
        LocalTime hrFechamento,
        LocalDate dtVencimento,
        LocalTime hrVencimento,
        Boolean flSlaViolado

) {
}
