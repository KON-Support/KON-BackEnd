package com.trier.KON_BackEnd.controller;

import com.trier.KON_BackEnd.dto.request.UsuarioRequestDTO;
import com.trier.KON_BackEnd.dto.response.UsuarioResponseDTO;
import com.trier.KON_BackEnd.services.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO usuarioCriado = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @GetMapping("/buscar/todos")
    public ResponseEntity<List<UsuarioResponseDTO>> buscarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(service.listar());

    }

    @PatchMapping("/desativar/{cdUsuario}")
    public ResponseEntity<UsuarioResponseDTO> desativar(@PathVariable Long cdUsuario) {
        var desativar = service.desativar(cdUsuario);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(desativar);
    }

    @PatchMapping("/reativar/{cdUsuario}")
    public ResponseEntity<UsuarioResponseDTO> reativar(@PathVariable @Valid Long cdUsuario) {
        var reativar = service.reativar(cdUsuario);
        return ResponseEntity.status(HttpStatus.OK).body(reativar);
    }

}
