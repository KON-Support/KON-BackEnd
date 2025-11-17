package com.trier.KON_BackEnd.controller;

import com.trier.KON_BackEnd.dto.request.ComentarioRequestDTO;
import com.trier.KON_BackEnd.dto.response.ComentarioResponseDTO;
import com.trier.KON_BackEnd.services.ComentarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/comentario")
@Tag(name = "Comentario", description = "Gerenciamento de comentários")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping("/criar")
    @Operation(summary = "Cria comentário", description = "Cria um comentário (mensagem) no chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comentário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados"),
            @ApiResponse(responseCode = "404", description = "Código do usuário ou chamado não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<ComentarioResponseDTO> criarComentario(@RequestBody
                                                                 @Valid ComentarioRequestDTO comentarioRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body((comentarioService.criarComentario(comentarioRequest))
        );
    }

    @GetMapping
    @Operation(summary = "Lista comentários", description = "Lista todos os comentários do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando todos os comentários"),
            @ApiResponse(responseCode = "404", description = "Nenhum comentário encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public ResponseEntity<List<ComentarioResponseDTO>> listarComentario() {
        return ResponseEntity.status(HttpStatus.OK).body(comentarioService.listarComentarios());
    }
}
