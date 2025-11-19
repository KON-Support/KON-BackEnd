package com.trier.KON_BackEnd.exception;

public class CategoriaNaoEncontradoException extends RuntimeException {

    public CategoriaNaoEncontradoException(Long cdCategoria) {
        super("Anexo não encontrado com o ID: " + cdCategoria);
    }

}
