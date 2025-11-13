package com.trier.KON_BackEnd.dto.response;

import com.trier.KON_BackEnd.enums.Prioridade;
import com.trier.KON_BackEnd.enums.Status;
import com.trier.KON_BackEnd.model.CategoriaModel;
import com.trier.KON_BackEnd.model.UsuarioModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ChamadoResponseDTO(

        Long cdChamado,
        String dsTitulo,
        String dsDescricao,
        Status status,
        UsuarioModel nmUsuario,
      /*Anexo cdAnexo,*/
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
