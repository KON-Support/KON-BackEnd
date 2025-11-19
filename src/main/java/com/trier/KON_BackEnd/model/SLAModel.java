package com.trier.KON_BackEnd.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBSLA")
public class SLAModel {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdSLA;

    private LocalDateTime qtHorasResposta;

    private LocalDateTime qtHorasResolucao;

    private String flAtivo = "S";

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "cdUsuario")
    private UsuarioModel usuario;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "cdCategoria")
    private CategoriaModel categoria;
}

