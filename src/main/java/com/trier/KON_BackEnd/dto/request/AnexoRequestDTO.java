package com.trier.KON_BackEnd.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record AnexoRequestDTO(
        @NotNull(message = "O código do chamado não pode ser vazio")
        Long cdChamado,

        @NotNull(message = "O código do usuário não pode ser vazio")
        Long cdUsuario,

        @NotNull(message = "O anexo não pode ser vazio")
        MultipartFile anexo
) {
}
