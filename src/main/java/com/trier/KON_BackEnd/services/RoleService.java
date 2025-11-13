package com.trier.KON_BackEnd.services;


import com.trier.KON_BackEnd.dto.request.RoleRequestDTO;
import com.trier.KON_BackEnd.dto.response.RoleResponseDTO;
import com.trier.KON_BackEnd.model.RoleModel;
import com.trier.KON_BackEnd.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    public final RoleRepository roleRepository;

    @Transactional
    public RoleResponseDTO cadastrarRole(RoleRequestDTO roleRequest) {

        var role = new RoleModel();
        role.setNmRole(roleRequest.nmRole());

        roleRepository.save(role);

        return new RoleResponseDTO(
                role.getCdRole(),
                role.getNmRole()
        );
    }


    public List<RoleModel> listar() {
        return roleRepository.findAll();
    }

    @Transactional
    public void deletar(Long cdRole) {

        RoleModel role = roleRepository.findById(cdRole)
                .orElseThrow(() -> new EntityNotFoundException("Role não encontrada para o ID: " + cdRole));

        roleRepository.delete(role);
    }
}