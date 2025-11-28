package com.trier.KON_BackEnd.repository;

import com.trier.KON_BackEnd.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;


public interface UsuarioRepository  extends JpaRepository<UsuarioModel,Long> {
    Optional<UsuarioModel> findByDsEmail(String dsEmail);

    @Query("select u from UsuarioModel u where u.flAtivo = true")
    List<UsuarioModel> findAllByFlAtivo();

}
