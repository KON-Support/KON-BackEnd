package com.trier.KON_BackEnd.repository;

import com.trier.KON_BackEnd.model.SLAModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SLARepository extends JpaRepository<SLAModel, Long> {
    Optional<SLAModel> findByCdSLA  ( Long cdSLA );
    List<SLAModel> findAll ();
}
