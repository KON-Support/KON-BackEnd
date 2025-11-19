package com.trier.KON_BackEnd.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    private ResponseEntity<?> UsuarioNaoEncontradoException(UsuarioNaoEncontradoException exception) {
        Map<String, Object> response = new HashMap<>();

        response.put("erro", exception.getMessage());

        return ResponseEntity.status(404).body(response);
    }
    @ExceptionHandler(ChamadoNaoEncontradoException.class)
    private ResponseEntity<?> ChamadoNaoEncontradoException(ChamadoNaoEncontradoException exception) {
        Map<String, Object> response = new HashMap<>();

        response.put("erro", exception.getMessage());

        return ResponseEntity.status(404).body(response);
    }
    @ExceptionHandler(AnexoNaoEncontradoException.class)
    private ResponseEntity<?> AnexoNaoEncontradoException(AnexoNaoEncontradoException exception) {
        Map<String, Object> response = new HashMap<>();

        response.put("erro", exception.getMessage());

        return ResponseEntity.status(404).body(response);
    }
    @ExceptionHandler(CategoriaNaoEncontradoException.class)
    private ResponseEntity<?> CategoriaNaoEncontradoException(CategoriaNaoEncontradoException exception) {
        Map<String, Object> response = new HashMap<>();

        response.put("erro", exception.getMessage());

        return ResponseEntity.status(404).body(response);
    }
    @ExceptionHandler(PlanoNaoEncontradoException.class)
    private ResponseEntity<?> PlanoNaoEncontradoException(PlanoNaoEncontradoException exception) {
        Map<String, Object> response = new HashMap<>();

        response.put("erro", exception.getMessage());

        return ResponseEntity.status(404).body(response);
    }
    @ExceptionHandler(NenhumPlanoEncontradoException.class)
    private ResponseEntity<?> NenhumPlanoEncontradoException(NenhumPlanoEncontradoException exception) {
        Map<String, Object> response = new HashMap<>();

        response.put("erro", exception.getMessage());

        return ResponseEntity.status(404).body(response);
    }
}
