package com.trier.KON_BackEnd.dto.response;

import com.trier.KON_BackEnd.enums.Status;
import java.time.LocalDate;
import java.time.LocalTime;

public record ChamadoResponseDTO(

        Long cdChamado,
        String dsTitulo,
        String dsDescricao,
        Status status,
        UsuarioSimplesDTO usuario,
        AnexoSimplesDTO anexo,
        CategoriaSimplesDTO categoria,
        SLASimplesDTO sla,
        LocalDate dtCriacao,
        LocalTime hrCriacao,
        LocalDate dtFechamento,
        LocalTime hrFechamento,
        LocalDate dtVencimento,
        LocalTime hrVencimento,
        Boolean flSlaViolado

) {

    public record UsuarioSimplesDTO(

            Long cdUsuario,
            String nmUsuario

    ) {}

    public record AnexoSimplesDTO(

            Long cdAnexo,
            String nmArquivo,
            String dsTipoArquivo

    ) {}

    public record CategoriaSimplesDTO(

            Long cdCategoria,
            String nmCategoria

    ) {}

    public record SLASimplesDTO(

            Long cdSLA,
            Integer qtHorasResposta,
            Integer qtHorasResolucao

    ) {}
}