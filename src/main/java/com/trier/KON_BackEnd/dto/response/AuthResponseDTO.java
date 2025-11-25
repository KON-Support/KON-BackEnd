package com.trier.KON_BackEnd.dto.response;


public record AuthResponseDTO(
        String token,
        UsuarioLoginDTO usuario
) {}
