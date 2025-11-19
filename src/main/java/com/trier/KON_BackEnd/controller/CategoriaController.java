package com.trier.KON_BackEnd.controller;

import com.trier.KON_BackEnd.dto.request.CategoriaRequestDTO;
import com.trier.KON_BackEnd.dto.request.CategoriaUpdateRequestDTO;
import com.trier.KON_BackEnd.dto.response.CategoriaResponseDTO;
import com.trier.KON_BackEnd.services.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/api/v1/categoria")
@Tag(name = "Categoria", description = "API para gerenciamento de categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar nova categoria", description = "Cria uma nova categoria no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoriaResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<CategoriaResponseDTO> cadastrarCategoria(
            @RequestBody @Valid CategoriaRequestDTO categoriaRequest) {

        var categoria = categoriaService.cadastrarCategoria(categoriaRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoria);
    }

    @PutMapping("/atualizar/{cdCategoria}")
    @Operation(summary = "Atualizar categoria", description = "Atualiza os dados de uma categoria existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoriaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<CategoriaResponseDTO> atualizarCategoria(
            @Parameter(description = "ID da categoria a ser atualizada", required = true)
            @PathVariable Long cdCategoria,
            @RequestBody @Valid CategoriaUpdateRequestDTO categoriaRequest) {

        var categoria = categoriaService.atualizarCategoria(cdCategoria, categoriaRequest);

        return ResponseEntity.status(HttpStatus.OK).body(categoria);
    }

    @PatchMapping("/desativar/{cdCategoria}")
    @Operation(summary = "Desativar categoria", description = "Desativa uma categoria existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria desativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<CategoriaResponseDTO> desativarCategoria(
            @Parameter(description = "ID da categoria a ser desativada", required = true)
            @PathVariable Long cdCategoria) {

        var categoria = categoriaService.desativarCategoria(cdCategoria);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(categoria);
    }

    @PatchMapping("/reativar/{cdCategoria}")
    @Operation(summary = "Reativar categoria", description = "Reativa uma categoria previamente desativada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria reativada com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoriaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<CategoriaResponseDTO> reativarCategoria(
            @Parameter(description = "ID da categoria a ser reativada", required = true)
            @PathVariable Long cdCategoria) {

        var categoria = categoriaService.reativarCategoria(cdCategoria);

        return ResponseEntity.status(HttpStatus.OK).body(categoria);
    }

    @GetMapping("/listar/{cdCategoria}")
    @Operation(summary = "Buscar categoria por ID", description = "Retorna os dados de uma categoria específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada",
                    content = @Content(schema = @Schema(implementation = CategoriaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<CategoriaResponseDTO> listarCategoria(
            @Parameter(description = "ID da categoria a ser buscada", required = true)
            @PathVariable Long cdCategoria) {

        var categoria = categoriaService.listarCategoria(cdCategoria);

        return ResponseEntity.status(HttpStatus.OK).body(categoria);
    }

    @GetMapping("/listar/ativas")
    @Operation(summary = "Listar categorias ativas", description = "Retorna todas as categorias ativas do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias ativas retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoriaResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<List<CategoriaResponseDTO>> listarCategoriasAtivas() {

        var categoria = categoriaService.listarCategoriasAtivas();

        return ResponseEntity.status(HttpStatus.OK).body(categoria);
    }

    @GetMapping("/listar/todas")
    @Operation(summary = "Listar todas as categorias", description = "Retorna todas as categorias do sistema (ativas e inativas)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de todas as categorias retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoriaResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<List<CategoriaResponseDTO>> listarTodasCategorias() {

        var categoria = categoriaService.listarTodasCategorias();

        return ResponseEntity.status(HttpStatus.OK).body(categoria);
    }
}