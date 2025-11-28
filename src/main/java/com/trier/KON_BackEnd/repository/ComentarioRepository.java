package com.trier.KON_BackEnd.repository;

import com.trier.KON_BackEnd.model.ComentarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ComentarioRepository extends JpaRepository<ComentarioModel, Long> {

    List<ComentarioModel> findAllByChamado_CdChamado(Long chamadoCdChamado);

}
