package com.trier.KON_BackEnd.dto.response;

import com.trier.KON_BackEnd.enums.Prioridade;
import com.trier.KON_BackEnd.enums.Status;

import java.time.LocalDateTime;

public record ChamadoResponseDTO(

        Long cdChamado,
        String dsTitulo,
        String dsDescricao,
        Status status,
        Prioridade prioridade,
        LocalDateTime dtCriacao,
        LocalDateTime dtAtualizacao,
        LocalDateTime dtFechamento,
        LocalDateTime dtVencimento,
        Boolean flSlaViolado

) {
}
