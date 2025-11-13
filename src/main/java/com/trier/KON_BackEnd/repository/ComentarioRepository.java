package com.trier.KON_BackEnd.repository;

import com.trier.KON_BackEnd.model.Comentario;
import com.trier.KON_BackEnd.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    List<Comentario> findAllByCdUsuario(Usuario cdUsuario);
}
