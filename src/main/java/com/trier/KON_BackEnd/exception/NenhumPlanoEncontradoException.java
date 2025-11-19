package com.trier.KON_BackEnd.exception;

public class NenhumPlanoEncontradoException extends RuntimeException {
    public NenhumPlanoEncontradoException() {
        super("Nenhum plano encontrado");
    }
}
