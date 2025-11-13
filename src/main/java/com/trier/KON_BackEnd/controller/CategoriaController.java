package com.trier.KON_BackEnd.controller;

import com.trier.KON_BackEnd.dto.request.CategoriaRequestDTO;
import com.trier.KON_BackEnd.dto.response.CategoriaResponseDTO;
import com.trier.KON_BackEnd.services.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categoria")

public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping("/cadastrar")
    public ResponseEntity<CategoriaResponseDTO> cadastrarCategoria(@RequestBody @Valid CategoriaRequestDTO categoriaRequest) {

        var categoria = categoriaService.cadastrarCategoria(categoriaRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);

    }

    @PutMapping("/atualizar/{cdCategoria}")
    public ResponseEntity<CategoriaResponseDTO> atualizarCategoria(@PathVariable Long cdCategoria,
                                                                   @RequestBody @Valid CategoriaRequestDTO categoriaRequest) {

        var categoria = categoriaService.atualizarCategoria(cdCategoria, categoriaRequest);

        return ResponseEntity.status(HttpStatus.OK).body(categoria);

    }

    @PatchMapping("/desativar/{cdCategoria}")
    public ResponseEntity<CategoriaResponseDTO> desativarCategoria(@PathVariable Long cdCategoria) {

        var categoria = categoriaService.desativarCategoria(cdCategoria);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(categoria);

    }

    @PutMapping("/reativar/{cdCategoria}")
    public ResponseEntity<CategoriaResponseDTO> reativarCategoria(@PathVariable Long cdCategoria) {

        var categoria = categoriaService.reativarCategoria(cdCategoria);

        return ResponseEntity.status(HttpStatus.OK).body(categoria);

    }

    @GetMapping("/listar/{cdCategoria}")
    public ResponseEntity<CategoriaResponseDTO> listarCategoria(@PathVariable Long cdCategoria) {

        var categoria = categoriaService.listarCategoria(cdCategoria);

        return ResponseEntity.status(HttpStatus.OK).body(categoria);

    }

    @GetMapping("/listar/ativas")
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategoriasAtivas() {

        var categoria = categoriaService.listarCategoriasAtivas();

        return ResponseEntity.status(HttpStatus.OK).body(categoria);

    }

    @GetMapping("/listar/todas")
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodasCategorias() {

        var categoria = categoriaService.listarTodasCategorias();

        return ResponseEntity.status(HttpStatus.OK).body(categoria);

    }

}
