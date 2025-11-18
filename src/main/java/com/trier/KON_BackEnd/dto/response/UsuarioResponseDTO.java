package com.trier.KON_BackEnd.dto.response;


import com.trier.KON_BackEnd.model.PlanoModel;

import java.time.LocalDateTime;

public record UsuarioResponseDTO (

        Long cdUsuario, String nmUsuario,

        String dsSenha,

        String dsEmail,

        LocalDateTime dtCriacao,

        LocalDateTime dtUltimoAcesso,

        boolean flAtivo,

        Integer nuFuncionario,

        PlanoModel plano

) {

}
