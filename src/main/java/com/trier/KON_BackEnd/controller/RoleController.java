package com.trier.KON_BackEnd.controller;


import com.trier.KON_BackEnd.dto.request.RoleRequestDTO;
import com.trier.KON_BackEnd.dto.response.RoleResponseDTO;
import com.trier.KON_BackEnd.dto.response.UsuarioResponseDTO;
import com.trier.KON_BackEnd.model.RoleModel;
import com.trier.KON_BackEnd.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/role")
@RequiredArgsConstructor
@Tag(name = "Role", description = "Gerenciamento de roles")
public class RoleController {

    private final RoleService service;

    @PostMapping("/cadastro")
    @Operation(summary = "Cadastrar nova role", description = "Cria uma nova role no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role criada com sucesso",
                    content = @Content(schema = @Schema(implementation = RoleResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<RoleResponseDTO> cadastrarRole(@RequestBody @Valid RoleRequestDTO roleRequest) {
        var role = service.cadastrarRole(roleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }

    @GetMapping("/buscar/todos")
    @Operation(summary = "Buscar todos as roles",
            description = "Retorna todos as roles cadastradas no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de roles retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = RoleResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<List<RoleModel>> buscarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(service.listar());
    }

    @DeleteMapping("/deletar/{cdRole}")
    @Operation(summary = "Deletar role", description = "Deleta uma role existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Role deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Role não encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<Void> deletarRole(@PathVariable Long cdRole) {
        service.deletar(cdRole);return ResponseEntity.noContent().build();
    }

}

