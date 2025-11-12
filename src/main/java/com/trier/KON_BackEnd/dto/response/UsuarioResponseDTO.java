package com.trier.KON_BackEnd.dto.response;


import java.time.LocalDateTime;

public record UsuarioResponseDTO (

        String nmUsuario,

        String dsSenha,

        String dsEmail,

        String dsAvatarUrl,

        LocalDateTime dtCriacao,

        LocalDateTime dtUltimoAcesso,

        boolean flAtivo


) {

}
