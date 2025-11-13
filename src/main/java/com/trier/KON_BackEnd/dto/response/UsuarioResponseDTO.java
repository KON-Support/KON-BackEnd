package com.trier.KON_BackEnd.dto.response;


import java.time.LocalDateTime;

public record UsuarioResponseDTO (

        Long cdUsuario, String nmUsuario,

        String dsSenha,

        String dsEmail,

        LocalDateTime dtCriacao,

        LocalDateTime dtUltimoAcesso,

        boolean flAtivo


) {

}
