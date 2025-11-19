package com.trier.KON_BackEnd.repository;

import com.trier.KON_BackEnd.model.PlanoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanoRepository extends JpaRepository<PlanoModel, Long> {
    List<PlanoModel> findAllByOrderByLimiteUsuariosAsc();
}
