package com.trier.KON_BackEnd.exception;

public class CategoriaNaoEncontradoException extends RuntimeException {

    public CategoriaNaoEncontradoException(Long cdCategoria) {
        super("Categoria não encontrada com o ID: " + cdCategoria);
    }

}
