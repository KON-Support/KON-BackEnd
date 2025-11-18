package com.trier.KON_BackEnd.exception;

public class AnexoNaoEncontradoException extends RuntimeException {
    public AnexoNaoEncontradoException(Long cdAnexo) {
        super("Anexo não encontrado com o ID: " + cdAnexo);
    }
}
