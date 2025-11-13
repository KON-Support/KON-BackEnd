package com.trier.KON_BackEnd.repository;

import com.trier.KON_BackEnd.model.AnexoModel;
import com.trier.KON_BackEnd.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnexoRepository extends JpaRepository<AnexoModel, Long> {
    List<AnexoModel> findByCdUsuario(UsuarioModel cdUsuario);
}
