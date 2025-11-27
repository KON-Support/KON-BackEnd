package com.trier.KON_BackEnd.dto.response;

import java.util.Set;

public record UsuarioLoginDTO(
        Long cdUsuario,
        String nmUsuario,
        Integer nuFuncionario,
        String dsEmail,
        Set<String> roles
) {}
