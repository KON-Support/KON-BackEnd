package com.trier.KON_BackEnd.exception;

public class ChamadoNaoEncontradoException extends RuntimeException {
    public ChamadoNaoEncontradoException(Long cdChamado) {
        super("Chamado não encontrado com o código: " + cdChamado);
    }
}
