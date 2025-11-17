package com.trier.KON_BackEnd.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record AnexoRequestDTO(
        @Schema(description = "Código do chamado")
        @NotNull(message = "O código do chamado não pode ser vazio")
        Long cdChamado,

        @Schema(description = "Código do usuário")
        @NotNull(message = "O código do usuário não pode ser vazio")
        Long cdUsuario,

        @Schema(description = "Arquivo que será enviado")
        @NotNull(message = "O anexo não pode ser vazio")
        MultipartFile anexo
) {
}
