package com.trier.KON_BackEnd.repository;

import com.trier.KON_BackEnd.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleModel,Long> {

    Optional<RoleModel> findByNmRole(String nmRole);

}
