package com.trier.KON_BackEnd.controller;


import com.trier.KON_BackEnd.dto.request.PlanoRequestDTO;
import com.trier.KON_BackEnd.dto.request.RoleRequestDTO;
import com.trier.KON_BackEnd.dto.response.PlanoResponseDTO;
import com.trier.KON_BackEnd.dto.response.RoleResponseDTO;
import com.trier.KON_BackEnd.services.PlanoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plano")
@RequiredArgsConstructor
public class PlanoController {

    private final PlanoService service;

    @PostMapping("/cadastro")
    public ResponseEntity<PlanoResponseDTO> cadastrarPlano(@RequestBody @Valid PlanoRequestDTO request) {
        var plano = service.cadastro(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(plano);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PlanoResponseDTO>> buscarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(service.listarTodos());
    }

}
