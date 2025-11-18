package com.trier.KON_BackEnd.controller;

import com.trier.KON_BackEnd.dto.request.ChamadoRequestDTO;
import com.trier.KON_BackEnd.dto.response.ChamadoResponseDTO;
import com.trier.KON_BackEnd.enums.Status;
import com.trier.KON_BackEnd.services.ChamadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chamado")

public class ChamadoController {

    @Autowired
    private ChamadoService chamadoService;

    @PostMapping("/abrir")
    public ResponseEntity<ChamadoResponseDTO> abrirChamado(@RequestBody @Valid ChamadoRequestDTO chamadoRequest) {

        var chamado = chamadoService.abrirChamado(chamadoRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(chamado);

    }

    @PutMapping("/atribuir/{cdChamado}")
    public ResponseEntity<ChamadoResponseDTO> atribuirChamado(@PathVariable
                                                                  Long cdChamado, Long cdUsuario,
                                                                  Long cdCategoria, Long cdSLA) {

        var chamado = chamadoService.atribuirChamado(cdUsuario, cdChamado, cdCategoria, cdSLA);

        return ResponseEntity.status(HttpStatus.OK).body(chamado);

    }

    @PutMapping("/atualizar/status/{cdChamado}")
    public ResponseEntity<ChamadoResponseDTO> atualizarStatus(@PathVariable Long cdChamado,
                                                              Status status) {

        var chamado = chamadoService.atualizarStatus(cdChamado, status);

        return ResponseEntity.status(HttpStatus.OK).body(chamado);

    }

    @PutMapping("/fechar/{cdChamado}")
    public ResponseEntity<ChamadoResponseDTO> fecharChamado(@PathVariable Long cdChamado,
                                                            Status status) {

        var chamado = chamadoService.fecharChamado(cdChamado, status);

        return ResponseEntity.status(HttpStatus.OK).body(chamado);

    }

    @PutMapping("/adicionar/anexo/{cdChamado}")
    public ResponseEntity<ChamadoResponseDTO> adicionarAnexo(@PathVariable Long cdChamado,
                                                             @RequestBody Long cdAnexo) {

        var chamado = chamadoService.adicionarAnexo(cdChamado, cdAnexo);

        return ResponseEntity.status(HttpStatus.OK).body(chamado);

    }

    @GetMapping("/listar/todos")
    public ResponseEntity<List<ChamadoResponseDTO>> listarTodosChamados() {

        var chamado = chamadoService.listarTodosChamados();

        return ResponseEntity.status(HttpStatus.OK).body(chamado);

    }

    @GetMapping("/listar/{cdChamado}")
    public ResponseEntity<ChamadoResponseDTO> listarChamado(@PathVariable Long cdChamado) {

        var chamado = chamadoService.listarChamado(cdChamado);

        return ResponseEntity.status(HttpStatus.OK).body(chamado);

    }

    @GetMapping("/listar/status/{status}")
    public ResponseEntity<List<ChamadoResponseDTO>> listarPorStatus(@PathVariable Status status) {

        var chamado = chamadoService.listarPorStatus(status);

        return ResponseEntity.status(HttpStatus.OK).body(chamado);

    }




}
