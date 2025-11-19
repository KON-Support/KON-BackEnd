package com.trier.KON_BackEnd.controller;


import com.trier.KON_BackEnd.dto.request.PlanoRequestDTO;
import com.trier.KON_BackEnd.dto.request.RoleRequestDTO;
import com.trier.KON_BackEnd.dto.response.AnexoResponseDTO;
import com.trier.KON_BackEnd.dto.response.PlanoResponseDTO;
import com.trier.KON_BackEnd.dto.response.RoleResponseDTO;
import com.trier.KON_BackEnd.services.PlanoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plano")
@RequiredArgsConstructor
@Tag(name = "Plano", description = "Gerenciamento de planos")
public class PlanoController {

    private final PlanoService service;

    @PostMapping("/cadastro")
    @Operation(summary = "Cadastrar plano", description = "Cadastra um novo plano no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plano cadastrado com sucesso",
                    content = @Content(schema = @Schema(implementation = PlanoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    public ResponseEntity<PlanoResponseDTO> cadastrarPlano(@RequestBody @Valid PlanoRequestDTO request) {
        var plano = service.cadastro(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(plano);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todos os planos", description = "Lista todos os planos do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando planos",
                    content = @Content(schema = @Schema(implementation = PlanoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum plano encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    public ResponseEntity<List<PlanoResponseDTO>> buscarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(service.listarTodos());
    }

    @PutMapping("/atualizar/{cdPlano}")
    @Operation(summary = "Atualizar plano", description = "Atualiza um plano pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano atualizado",
                    content = @Content(schema = @Schema(implementation = PlanoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum plano encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    public ResponseEntity<PlanoResponseDTO> atualizarPlano(@PathVariable("cdPlano") Long cdPlano,
                                                           @RequestBody PlanoRequestDTO request) {

        return ResponseEntity.status(HttpStatus.OK).body(service.atualizarPlano(request, cdPlano));
    }

}
