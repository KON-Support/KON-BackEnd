package com.trier.KON_BackEnd.controller;

import com.trier.KON_BackEnd.dto.request.ComentarioAnexoRequestDTO;
import com.trier.KON_BackEnd.dto.request.ComentarioRequestDTO;
import com.trier.KON_BackEnd.dto.response.ComentarioResponseDTO;
import com.trier.KON_BackEnd.services.ComentarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/comentario")
@Tag(name = "Comentario", description = "Gerenciamento de comentários")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping("/criar")
    @Operation(summary = "Cria comentário", description = "Cria um comentário (mensagem) no chat")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comentário criado com sucesso",
            content = @Content(schema = @Schema(implementation = ComentarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados", content = @Content),
            @ApiResponse(responseCode = "404", description = "Código do usuário ou chamado não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor",  content = @Content)
    })
    public ResponseEntity<ComentarioResponseDTO> criarComentario(@RequestBody
                                                                 @Valid ComentarioRequestDTO comentarioRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body((comentarioService.criarComentario(comentarioRequest))
        );
    }

    @PostMapping(value = "/criar-com-anexo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Cria comentário com anexo",
            description = "Cria um comentário com arquivo anexo vinculado ao chamado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comentário com anexo criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ComentarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro na validação dos dados", content = @Content),
            @ApiResponse(responseCode = "404", description = "Código do usuário ou chamado não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    public ResponseEntity<ComentarioResponseDTO> criarComentarioComAnexo(
            @ModelAttribute @Valid ComentarioAnexoRequestDTO comentarioRequest) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(comentarioService.criarComentarioComAnexo(comentarioRequest));
    }

    @GetMapping("/chamado/{cdChamado}")
    @Operation(summary = "Lista comentários", description = "Lista todos os comentários de um chamado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando todos os comentários",
            content = @Content(schema = @Schema(implementation = ComentarioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum comentário encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    public ResponseEntity<List<ComentarioResponseDTO>> listarComentario(@PathVariable Long cdChamado) {

        return ResponseEntity.status(HttpStatus.OK).body(comentarioService.listarComentarios(cdChamado));
    }
}
