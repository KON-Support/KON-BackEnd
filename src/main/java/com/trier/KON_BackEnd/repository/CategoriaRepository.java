package com.trier.KON_BackEnd.repository;

import com.trier.KON_BackEnd.model.CategoriaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {

    Optional<CategoriaModel> findByName(String nmCategoria);

    @Query("select p from CategoriaModel p where p.flAtivo = true")
    List<CategoriaModel> findAllByFlAtivo();

}
