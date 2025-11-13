package com.trier.KON_BackEnd.repository;

import com.trier.KON_BackEnd.model.CategoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {

    Optional<CategoriaModel> findByName(String nmCategoria);

}
