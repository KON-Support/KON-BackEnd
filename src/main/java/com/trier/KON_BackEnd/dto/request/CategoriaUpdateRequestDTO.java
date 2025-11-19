package com.trier.KON_BackEnd.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaUpdateRequestDTO(

        @NotBlank(message = "É preciso informar um nome para uma Categoria!")
        @Size(max = 255, message= "O máximo de caracteres que pode ter é 255!")
        String nmCategoria,

        Boolean flAtivo

) {
}