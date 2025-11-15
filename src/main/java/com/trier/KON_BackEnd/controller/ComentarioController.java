package com.trier.KON_BackEnd.controller;

import com.trier.KON_BackEnd.dto.request.ComentarioRequestDTO;
import com.trier.KON_BackEnd.dto.response.ComentarioResponseDTO;
import com.trier.KON_BackEnd.services.ComentarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/comentario")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping("/criar")
    public ResponseEntity<ComentarioResponseDTO> criarComentario(@RequestBody
                                                                 @Valid ComentarioRequestDTO comentarioRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body((comentarioService.criarComentario(comentarioRequest))
        );
    }

    @GetMapping
    public ResponseEntity<List<ComentarioResponseDTO>> listarComentario() {
        return ResponseEntity.status(HttpStatus.OK).body(comentarioService.listarComentarios());
    }
}
