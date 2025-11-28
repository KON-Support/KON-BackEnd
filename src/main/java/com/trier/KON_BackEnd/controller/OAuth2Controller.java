package com.trier.KON_BackEnd.controller;

import com.trier.KON_BackEnd.dto.request.CompletarCadastroOAuthRequestDTO;
import com.trier.KON_BackEnd.dto.response.AuthResponseDTO;
import com.trier.KON_BackEnd.services.OAuth2Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth2")
@RequiredArgsConstructor
@Tag(name = "OAuth2", description = "Gerenciamento de autenticação OAuth2")
public class OAuth2Controller {

    private final OAuth2Service oauth2Service;

    @PostMapping("/completar-cadastro")
    @Operation(summary = "Completar cadastro OAuth2", description = "Completa o cadastro de usuário OAuth2 com número de funcionários")
    public ResponseEntity<AuthResponseDTO> completarCadastro(
            @Valid @RequestBody CompletarCadastroOAuthRequestDTO dto) {

        AuthResponseDTO authResponse = oauth2Service.completarCadastroOAuth(
                dto.email(), dto.nome(), (dto.nuFuncionario()));

        return ResponseEntity.ok(authResponse);
    }
}
