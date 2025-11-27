package com.trier.KON_BackEnd.controller;

import com.trier.KON_BackEnd.dto.response.AuthResponseDTO;
import com.trier.KON_BackEnd.services.OAuth2Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação OAuth2", description = "API para autenticação com Google OAuth2")
public class OAuth2Controller {

    private final OAuth2Service oAuth2Service;

    @Value("${frontend.url}")
    private String frontendUrl;

    @GetMapping("/oauth2/success")
    @Operation(summary = "Callback de sucesso OAuth2",
            description = "Processa o login bem-sucedido do Google OAuth2 e redireciona para o frontend")
    public void oauth2LoginSuccess(
            @AuthenticationPrincipal OAuth2User oAuth2User,
            HttpServletResponse response) throws IOException {

        try {
            AuthResponseDTO authResponse = oAuth2Service.processOAuthPostLogin(oAuth2User);

            String redirectUrl = UriComponentsBuilder.fromUriString(frontendUrl + "/oauth2/redirect")
                    .queryParam("token", authResponse.token())
                    .queryParam("userId", authResponse.usuario().cdUsuario())
                    .queryParam("userName", URLEncoder.encode(authResponse.usuario().nmUsuario(), StandardCharsets.UTF_8))
                    .queryParam("userEmail", URLEncoder.encode(authResponse.usuario().dsEmail(), StandardCharsets.UTF_8))
                    .build()
                    .toUriString();

            response.sendRedirect(redirectUrl);
        } catch (Exception e) {
            String errorUrl = frontendUrl + "/oauth2/redirect?error=" +
                    URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            response.sendRedirect(errorUrl);
        }
    }

    @GetMapping("/oauth2/failure")
    @Operation(summary = "Callback de falha OAuth2",
            description = "Trata erros durante o processo de autenticação OAuth2")
    public void oauth2LoginFailure(HttpServletResponse response) throws IOException {
        String errorUrl = frontendUrl + "/oauth2/redirect?error=authentication_failed";
        response.sendRedirect(errorUrl);
    }
}