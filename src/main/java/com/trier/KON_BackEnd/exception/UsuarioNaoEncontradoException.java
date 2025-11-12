package com.trier.KON_BackEnd.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsuarioNaoEncontradoException extends RuntimeException {
    public UsuarioNaoEncontradoException(Long cdUsuario) {
        super("Usuário não encontrado com o ID: " + cdUsuario);
    }
}
