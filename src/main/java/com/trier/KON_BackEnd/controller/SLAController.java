package com.trier.KON_BackEnd.controller;

import com.trier.KON_BackEnd.dto.sla.SLARequestDto;
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
@RequestMapping("/sla")
@Tag(name = "SLA", description = "Capacidade de gerenciamento dos SLA")
@CrossOrigin(origins = "*")
public class SLAController {

    @Autowired
    private SLAService slaService;
    private SLARepository slaRepository;

    @PostMapping("/criar")
    @Operation(summary = "Cadastrar novo SLA", description = "Cria um novo sla no sistema")
    public ResponseEntity<SLAResponseDto> criarSLA(@Valid @RequestBody SLARequestDto slaRequestDto){
        SLAResponseDto criardSla = slaService.criasSLA(slaRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(criardSla);
    }
}
