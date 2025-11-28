package com.trier.KON_BackEnd.dto.request;

public record AtualizarUsuarioDTO(
        String nmUsuario,

        String dsEmail,

        String dsSenha,

        Integer nuFuncionario
) {
}
