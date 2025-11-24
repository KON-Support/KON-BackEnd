package com.trier.KON_BackEnd.exception;

public class PlanoNaoEncontradoException extends RuntimeException {
    public PlanoNaoEncontradoException(Long cdPlano) {
        super("Plano não encontrado com o ID: " + cdPlano);
    }
}
