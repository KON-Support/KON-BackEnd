package com.trier.KON_BackEnd.controller;

import com.trier.KON_BackEnd.dto.request.AtribuirChamadoRequestDTO;
import com.trier.KON_BackEnd.dto.request.ChamadoRequestDTO;
import com.trier.KON_BackEnd.dto.response.ChamadoResponseDTO;
import com.trier.KON_BackEnd.enums.Status;
import com.trier.KON_BackEnd.services.ChamadoService;
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
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/chamado")
@Tag(name = "Chamado", description = "Gerenciamento de chamados (tickets)")
public class ChamadoController {

    @Autowired
    private ChamadoService chamadoService;

    @PostMapping("/abrir")
    @Operation(summary = "Abrir novo chamado", description = "Cria um novo chamado no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Chamado criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ChamadoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<ChamadoResponseDTO> abrirChamado(
            @RequestBody @Valid ChamadoRequestDTO chamadoRequest) {

        var chamado = chamadoService.abrirChamado(chamadoRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(chamado);

    }

    @PutMapping("/atribuir/{cdChamado}")
    @Operation(summary = "Atribuir chamado",
            description = "Atribui responsável (atendente), categoria e SLA a um chamado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chamado atribuído com sucesso",
                    content = @Content(schema = @Schema(implementation = ChamadoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Chamado, responsável, categoria ou SLA não encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<ChamadoResponseDTO> atribuirChamado(
            @Parameter(description = "ID do chamado", required = true, example = "1")
            @PathVariable Long cdChamado,

            @RequestBody @Valid AtribuirChamadoRequestDTO request) {

        var chamado = chamadoService.atribuirChamado(
                cdChamado,
                request.responsavel(),
                request.cdCategoria(),
                request.cdSLA()
        );

        return ResponseEntity.status(HttpStatus.OK).body(chamado);
    }

    @PutMapping("/atualizar/status/{cdChamado}")
    @Operation(summary = "Atualizar status", description = "Atualiza o status de um chamado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ChamadoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Status inválido ou chamado já fechado",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Chamado não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<ChamadoResponseDTO> atualizarStatus(
            @Parameter(description = "ID do chamado", required = true, example = "1")
            @PathVariable Long cdChamado,

            @Parameter(description = "Novo status (ABERTO, EM_ANDAMENTO, RESOLVIDO, FECHADO)", required = true, example = "EM_ANDAMENTO")
            @RequestParam Status status) {

        var chamado = chamadoService.atualizarStatus(cdChamado, status);

        return ResponseEntity.status(HttpStatus.OK).body(chamado);

    }

    @PutMapping("/fechar/{cdChamado}")
    @Operation(summary = "Fechar chamado",
            description = "Fecha um chamado e registra data/hora de fechamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chamado fechado com sucesso",
                    content = @Content(schema = @Schema(implementation = ChamadoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Chamado já está fechado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Chamado não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<ChamadoResponseDTO> fecharChamado(
            @Parameter(description = "ID do chamado", required = true, example = "1")
            @PathVariable Long cdChamado,

            @Parameter(description = "Status de fechamento (RESOLVIDO ou FECHADO)", required = true, example = "FECHADO")
            @RequestParam Status status) {

        var chamado = chamadoService.fecharChamado(cdChamado, status);

        return ResponseEntity.status(HttpStatus.OK).body(chamado);

    }

    @PutMapping("/adicionar/anexo/{cdChamado}")
    @Operation(summary = "Adicionar anexo",
            description = "Adiciona um anexo existente a um chamado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anexo adicionado com sucesso",
                    content = @Content(schema = @Schema(implementation = ChamadoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Chamado ou anexo não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<ChamadoResponseDTO> adicionarAnexo(
            @Parameter(description = "ID do chamado", required = true, example = "1")
            @PathVariable Long cdChamado,

            @Parameter(description = "ID do anexo", required = true, example = "1")
            @RequestBody Long cdAnexo) {

        var chamado = chamadoService.adicionarAnexo(cdChamado, cdAnexo);

        return ResponseEntity.status(HttpStatus.OK).body(chamado);

    }

    @GetMapping("/listar/todos")
    @Operation(summary = "Listar todos os chamados",
            description = "Retorna todos os chamados cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de chamados retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = ChamadoResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<List<ChamadoResponseDTO>> listarTodosChamados() {

        var chamados = chamadoService.listarTodosChamados();

        return ResponseEntity.status(HttpStatus.OK).body(chamados);

    }

    @GetMapping("/listar/{cdChamado}")
    @Operation(summary = "Buscar chamado por ID",
            description = "Retorna os dados de um chamado específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chamado encontrado",
                    content = @Content(schema = @Schema(implementation = ChamadoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Chamado não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<ChamadoResponseDTO> listarChamado(
            @Parameter(description = "ID do chamado", required = true, example = "1")
            @PathVariable Long cdChamado) {

        var chamado = chamadoService.listarChamado(cdChamado);

        return ResponseEntity.status(HttpStatus.OK).body(chamado);

    }

    @GetMapping("/listar/status/{status}")
    @Operation(summary = "Listar chamados por status",
            description = "Retorna todos os chamados com um status específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de chamados retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = ChamadoResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<List<ChamadoResponseDTO>> listarPorStatus(
            @Parameter(description = "Status do chamado (ABERTO, EM_ANDAMENTO, RESOLVIDO, FECHADO)",
                    required = true, example = "ABERTO")
            @PathVariable Status status) {

        var chamados = chamadoService.listarPorStatus(status);

        return ResponseEntity.status(HttpStatus.OK).body(chamados);

    }

    @GetMapping("/listar/solicitante/{cdUsuario}")
    @Operation(summary = "Listar chamados por solicitante",
            description = "Retorna todos os chamados abertos por um solicitante específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de chamados retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = ChamadoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<List<ChamadoResponseDTO>> listarPorSolicitante(
            @Parameter(description = "ID do usuário solicitante", required = true, example = "1")
            @PathVariable Long cdUsuario) {

        var chamados = chamadoService.listarPorSolicitante(cdUsuario);

        return ResponseEntity.status(HttpStatus.OK).body(chamados);
    }

    @GetMapping("/listar/responsavel/{cdUsuario}")
    @Operation(summary = "Listar chamados por responsável",
            description = "Retorna todos os chamados atribuídos a um responsável (atendente) específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de chamados retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = ChamadoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<List<ChamadoResponseDTO>> listarPorResponsavel(
            @Parameter(description = "ID do usuário responsável (atendente)", required = true, example = "2")
            @PathVariable Long cdUsuario) {

        var chamados = chamadoService.listarPorResponsavel(cdUsuario);

        return ResponseEntity.status(HttpStatus.OK).body(chamados);
    }

    @GetMapping("/listar/nao-atribuidos")
    @Operation(summary = "Listar chamados não atribuídos",
            description = "Retorna todos os chamados que ainda não possuem um responsável atribuído")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de chamados não atribuídos retornada com sucesso",
                    content = @Content(schema = @Schema(implementation = ChamadoResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content)
    })
    public ResponseEntity<List<ChamadoResponseDTO>> listarNaoAtribuidos() {

        var chamados = chamadoService.listarNaoAtribuidos();

        return ResponseEntity.status(HttpStatus.OK).body(chamados);
    }

}