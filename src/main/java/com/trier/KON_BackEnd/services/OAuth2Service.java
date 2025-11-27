package com.trier.KON_BackEnd.services;

import com.trier.KON_BackEnd.dto.response.AuthResponseDTO;
import com.trier.KON_BackEnd.dto.response.UsuarioLoginDTO;
import com.trier.KON_BackEnd.model.PlanoModel;
import com.trier.KON_BackEnd.model.RoleModel;
import com.trier.KON_BackEnd.model.UsuarioModel;
import com.trier.KON_BackEnd.repository.PlanoRepository;
import com.trier.KON_BackEnd.repository.RoleRepository;
import com.trier.KON_BackEnd.repository.UsuarioRepository;
import com.trier.KON_BackEnd.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OAuth2Service {

    private final UsuarioRepository usuarioRepository;

    private final RoleRepository roleRepository;

    private final PlanoRepository planoRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Transactional
    public AuthResponseDTO processOAuthPostLogin(OAuth2User oAuth2User) {

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        UsuarioModel usuario = usuarioRepository.findByDsEmail(email)
                .orElseGet(() -> criarNovoUsuarioOAuth(email, name));

        UserDetails userDetails = criarUserDetails(usuario);
        String token = jwtUtil.generateToken(userDetails);

        UsuarioLoginDTO usuarioDTO = new UsuarioLoginDTO(
                usuario.getCdUsuario(),
                usuario.getNmUsuario(),
                usuario.getDsEmail(),
                usuario.getRoleModel().stream()
                        .map(RoleModel::getNmRole)
                        .collect(Collectors.toSet())
        );

        return new AuthResponseDTO(token, usuarioDTO);
    }

    private UsuarioModel criarNovoUsuarioOAuth(String email, String name) {

        UsuarioModel usuario = new UsuarioModel();
        usuario.setDsEmail(email);
        usuario.setNmUsuario(name);
        usuario.setDsSenha(passwordEncoder.encode(UUID.randomUUID().toString()));
        usuario.setFlAtivo(true);
        usuario.setNuFuncionario(1);

        Set<RoleModel> roles = new HashSet<>();
        RoleModel roleUser = roleRepository.findByNmRole("ROLE_USER")
                .orElseGet(() -> {
                    RoleModel newRole = new RoleModel();
                    newRole.setNmRole("ROLE_USER");
                    return roleRepository.save(newRole);
                });
        roles.add(roleUser);
        usuario.setRoleModel(roles);

        List<PlanoModel> planos = planoRepository.findAllByOrderByLimiteUsuariosAsc();
        if (!planos.isEmpty()) {
            usuario.setPlano(planos.get(0));
        }

        return usuarioRepository.save(usuario);
    }

    private UserDetails criarUserDetails(UsuarioModel usuario) {
        List<GrantedAuthority> authorities = usuario.getRoleModel().stream()
                .map(role -> new SimpleGrantedAuthority(role.getNmRole()))
                .collect(Collectors.toList());

        return new User(
                usuario.getDsEmail(),
                usuario.getDsSenha(),
                authorities
        );
    }
}