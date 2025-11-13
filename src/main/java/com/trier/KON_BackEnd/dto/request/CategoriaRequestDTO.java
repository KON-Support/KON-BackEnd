package com.trier.KON_BackEnd.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoriaRequestDTO(

        @NotBlank(message = "É preciso informar um nome para uma Categoria!")
        @Size(max = 255, message= "O máximo de caracateres que pode ter é 255!")
        String nmCategoria,

        @NotNull(message = "É preciso informar se a categoria está ativa!")
        Boolean flAtivo

) {
}
