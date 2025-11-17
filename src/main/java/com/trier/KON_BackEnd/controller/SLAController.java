package com.trier.KON_BackEnd.controller;

import com.trier.KON_BackEnd.dto.sla.SLARequestDto;
import com.trier.KON_BackEnd.dto.sla.SLAResponseListar;
import com.trier.KON_BackEnd.dto.sla.response.SLAResponseDto;
import com.trier.KON_BackEnd.model.SLAModel;
import com.trier.KON_BackEnd.repository.SLARepository;
import com.trier.KON_BackEnd.services.SLAService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sla")
@Tag(name = "SLA", description = "Capacidade de gerenciamento dos SLA")
@CrossOrigin(origins = "*")
public class SLAController {

    @Autowired
    private SLAService slaService;
    private SLARepository slaRepository;

    @PostMapping("/criar")
    @Operation(summary = "Cadastrar novo SLA", description = "Cria um novo sla no sistema")
    public ResponseEntity<SLAResponseDto> criarSLA(@Valid @RequestBody SLARequestDto slaRequestDto) {
        SLAResponseDto criardSla = slaService.criasSLA(slaRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criardSla);
    }

    @PutMapping("/atualizar/{cdSLA}")
    @Operation(summary = "Atualizar um SLA", description = "Atualiza um SLA já cadastrado")
    public ResponseEntity<SLAResponseDto> atualizarSLA(@PathVariable Long cdSLA,
                                                       @Valid @RequestBody SLARequestDto slaResquest) {

        try {
            SLAResponseDto atualizado = slaService.autalizarSLA(slaResquest, cdSLA);
            System.out.println("SLA atualizado");
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/listar/todos")
    @Operation(summary = "Listar os SLA", description = "Lista todos os SLA cadastrados")
    public ResponseEntity<List<SLAResponseListar>> listarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(slaService.listarSLA());
    }

    @GetMapping("/categoria/{cdCategoria}")
    @Operation(summary = "Buscar SLA por categoria", description = "Retorna todos os SLAs vinculados a uma categoria específica")
    public ResponseEntity<List<SLAResponseListar>> buscarPorCategoria(@PathVariable Long cdCategoria) {
        try {
            List<SLAResponseListar> slas = slaService.buscarPorCategoria(cdCategoria);
            return ResponseEntity.ok(slas);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
