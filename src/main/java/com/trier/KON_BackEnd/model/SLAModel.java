package com.trier.KON_BackEnd.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

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

    private boolean flAtivo = true;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "cdPlano")
    private PlanoModel plano;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "cdCategoria")
    private CategoriaModel categoria;
}

