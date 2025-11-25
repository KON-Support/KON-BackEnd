package com.trier.KON_BackEnd.dto.response;

import com.trier.KON_BackEnd.enums.Status;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ChamadoResponseDTO(

        Long cdChamado,
        String dsTitulo,
        String dsDescricao,
        Status status,
        UsuarioSimplesDTO solicitante,
        UsuarioSimplesDTO responsavel,
        AnexoSimplesDTO anexo,
        CategoriaSimplesDTO categoria,
        SLASimplesDTO sla,
        LocalDateTime dtCriacao,
        LocalDateTime dtFechamento,
        LocalDateTime dtVencimento,
        Boolean flSlaViolado

) {

    public record UsuarioSimplesDTO(

            Long cdUsuario,
            String nmUsuario

    ) {}

    public record AnexoSimplesDTO(

            Long cdAnexo,
            String nmArquivo

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