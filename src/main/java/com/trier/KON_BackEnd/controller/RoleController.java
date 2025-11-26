package com.trier.KON_BackEnd.controller;


import com.trier.KON_BackEnd.dto.request.RoleRequestDTO;
import com.trier.KON_BackEnd.dto.response.RoleResponseDTO;
import com.trier.KON_BackEnd.model.RoleModel;
import com.trier.KON_BackEnd.services.RoleService;
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
@Tag(name = "Usuário", description = "Gerenciamento de usuários")
public class RoleController {

    private final RoleService service;

    @PostMapping("/cadastro")
    public ResponseEntity<RoleResponseDTO> cadastrarRole(@RequestBody @Valid RoleRequestDTO roleRequest) {
        var role = service.cadastrarRole(roleRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }

    @GetMapping("/buscar/todos")
    public ResponseEntity<List<RoleModel>> buscarTodos() {
        return ResponseEntity.status(HttpStatus.OK).body(service.listar());
    }









}
