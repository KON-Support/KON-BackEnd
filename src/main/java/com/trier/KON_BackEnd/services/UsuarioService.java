package com.trier.KON_BackEnd.services;


import com.trier.KON_BackEnd.dto.request.UsuarioRequestDTO;
import com.trier.KON_BackEnd.dto.response.UsuarioResponseDTO;
import com.trier.KON_BackEnd.model.RoleModel;
import com.trier.KON_BackEnd.model.UsuarioModel;
import com.trier.KON_BackEnd.repository.RoleRepository;
import com.trier.KON_BackEnd.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;

    @Transactional
    public UsuarioResponseDTO salvar(UsuarioRequestDTO dto) {
        if (usuarioRepository.findByDsEmail(dto.dsEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado!");
        }

        UsuarioModel usuario = new UsuarioModel();
        usuario.setNmUsuario(dto.nmUsuario());
        usuario.setDsSenha(dto.dsSenha());
        usuario.setDsEmail(dto.dsSenha());
        usuario.setFlAtivo(dto.flAtivo());

        Set<RoleModel> roles = new HashSet<>();

        if (dto.roles() != null && !dto.roles().isEmpty()) {
            for (String roleName : dto.roles()) {
                RoleModel role = roleRepository.findByNmRole(roleName)
                        .orElseGet(() -> {
                            RoleModel newRole = new RoleModel();
                            newRole.setNmRole(roleName);
                            return roleRepository.save(newRole);
                        });
                roles.add(role);
            }
        } else {
            RoleModel roleUser = roleRepository.findByNmRole("ROLE_USER")
                    .orElseGet(() -> {
                        RoleModel newRole = new RoleModel();
                        newRole.setNmRole("ROLE_USER");
                        return roleRepository.save(newRole);
                    });
            roles.add(roleUser);
        }

        usuario.setRoleModel(roles);

        UsuarioModel usuarioSalvo = usuarioRepository.save(usuario);
        return converterParaResponse(usuarioSalvo);
    }

    public List<UsuarioResponseDTO> listar() {
        List<UsuarioModel> usuario = usuarioRepository.findAllByFlAtivo();
        return usuario.stream().map(this::converterParaResponse).collect(Collectors.toList());
    }


    @Transactional
    public UsuarioResponseDTO desativar(Long cdUsuario) {
        UsuarioModel usuario = usuarioRepository.findById(cdUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        usuario.setFlAtivo(false);
        usuarioRepository.save(usuario);

        return converterParaResponse(usuario);
    }

    @Transactional
    public UsuarioResponseDTO reativar(Long cdUsuario) {
        UsuarioModel usuario = usuarioRepository.findById(cdUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        usuario.setFlAtivo(true);
        usuarioRepository.save(usuario);

        return converterParaResponse(usuario);
    }

    private UsuarioResponseDTO converterParaResponse(UsuarioModel usuario) {
        return new UsuarioResponseDTO(
                usuario.getCdUsuario(),
                usuario.getNmUsuario(),
                usuario.getDsEmail(),
                usuario.getDsSenha(),
                usuario.getDtCriacao(),
                usuario.getDtUltimoAcesso(),
                usuario.isFlAtivo()

        );
    }
}