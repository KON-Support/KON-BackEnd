package com.trier.KON_BackEnd.repository;

import com.trier.KON_BackEnd.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository  extends JpaRepository<Usuario,Long> {

}
