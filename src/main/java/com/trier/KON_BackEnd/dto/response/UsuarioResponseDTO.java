package com.trier.KON_BackEnd.dto.response;

import com.trier.KON_BackEnd.model.PlanoModel;
import com.trier.KON_BackEnd.model.RoleModel;
import com.trier.KON_BackEnd.model.UsuarioModel;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record UsuarioResponseDTO (
        Long cdUsuario,
        String nmUsuario,
        String dsEmail,
        LocalDateTime dtCriacao,
        LocalDateTime dtUltimoAcesso,
        boolean flAtivo,
        Integer nuFuncionario,
        PlanoModel plano,
        Set<String> roles
) {
    public UsuarioResponseDTO(UsuarioModel usuario) {
        this(
                usuario.getCdUsuario(),
                usuario.getNmUsuario(),
                usuario.getDsEmail(),
                usuario.getDtCriacao(),
                usuario.getDtUltimoAcesso(),
                usuario.isFlAtivo(),
                usuario.getNuFuncionario(),
                usuario.getPlano(),
                usuario.getRoleModel().stream()
                        .map(RoleModel::getNmRole)
                        .collect(Collectors.toSet())
        );
    }
}