package com.trier.KON_BackEnd.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompletarCadastroOAuthRequestDTO(
        @NotBlank String email,
        @NotBlank String nome,
        @NotNull @Min(1) @Max(10000000) Integer nuFuncionario
) {}