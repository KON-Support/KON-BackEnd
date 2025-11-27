package com.trier.KON_BackEnd.controller;

import com.trier.KON_BackEnd.dto.request.AtualizarUsuarioDTO;
import com.trier.KON_BackEnd.dto.request.UsuarioRequestDTO;
import com.trier.KON_BackEnd.dto.response.CategoriaResponseDTO;
import com.trier.KON_BackEnd.dto.response.UsuarioResponseDTO;
import com.trier.KON_BackEnd.services.UsuarioService;
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
@RequestMapping("/api/usuario")
@RequiredArgsConstructor
@Tag(name = "Usuário", description = "Gerenciamento de usuários")
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping("/cadastro")
    @Operation(summary = "Cadastrar novo usuário", description = "Cria um novo usuário no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO usuarioCriado = service.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @GetMapping("/buscar/todos")
    @Operation(summary = "Buscar todos os usuários",
            description = "Retorna todos os usuários cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<List<UsuarioResponseDTO>> buscarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(service.listar());

    }

    @PutMapping("/desativar/{cdUsuario}")
    @Operation(summary = "Desativar usuário", description = "Desativa um usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário desativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<UsuarioResponseDTO> desativar(@PathVariable Long cdUsuario) {
        var desativar = service.desativar(cdUsuario);
        return ResponseEntity.status(HttpStatus.OK).body(desativar);
    }

    @PutMapping("/reativar/{cdUsuario}")
    @Operation(summary = "Reativar usuário", description = "Reativa um usuário previamente desativado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário reativado com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoriaResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<UsuarioResponseDTO> reativar(@PathVariable @Valid Long cdUsuario) {
        var reativar = service.reativar(cdUsuario);
        return ResponseEntity.status(HttpStatus.OK).body(reativar);
    }

    @PutMapping("/atualizar/{cdUsuario}")
    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = UsuarioResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<UsuarioResponseDTO> atualizar(@PathVariable Long cdUsuario,
                                                        @Valid @RequestBody AtualizarUsuarioDTO dto) {
        var atualizado = service.atualizar(dto, cdUsuario);
        return ResponseEntity.status(HttpStatus.OK).body(atualizado);
    }

}
