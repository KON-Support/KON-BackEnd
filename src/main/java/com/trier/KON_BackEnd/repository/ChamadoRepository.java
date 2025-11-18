package com.trier.KON_BackEnd.repository;

import com.trier.KON_BackEnd.enums.Status;
import com.trier.KON_BackEnd.model.ChamadoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ChamadoRepository extends JpaRepository<ChamadoModel, Long> {

    List<ChamadoModel> findAllByStatus(Status status);

}
