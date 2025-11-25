package com.trier.KON_BackEnd.controller;

import com.trier.KON_BackEnd.dto.request.AuthRequestDTO;
import com.trier.KON_BackEnd.dto.response.AuthResponseDTO;
import com.trier.KON_BackEnd.dto.response.UsuarioLoginDTO;
import com.trier.KON_BackEnd.model.UsuarioModel;
import com.trier.KON_BackEnd.repository.UsuarioRepository;
import com.trier.KON_BackEnd.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticação", description = "API para autenticação de usuários")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    @Operation(summary = "Realizar login", description = "Autentica o usuário e retorna um token JWT")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.dsEmail(),
                            authRequest.dsSenha()
                    )
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.dsEmail());
            String token = jwtUtil.generateToken(userDetails);

            UsuarioModel usuario = usuarioRepository.findByDsEmail(authRequest.dsEmail())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            UsuarioLoginDTO usuarioDTO = new UsuarioLoginDTO(
                    usuario.getCdUsuario(),
                    usuario.getNmUsuario(),
                    usuario.getDsEmail(),
                    usuario.getRoleModel()
                            .stream()
                            .map(role -> role.getNmRole())
                            .collect(Collectors.toSet())
            );

            return ResponseEntity.ok(new AuthResponseDTO(token, usuarioDTO));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
