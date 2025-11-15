package com.trier.KON_BackEnd.exception;

public class SLANaoEncontradoException extends RuntimeException {
    public SLANaoEncontradoException(String cdSLA) {
        super("SLA com o cdSLA " + cdSLA + " não encontado.");
    }
}
