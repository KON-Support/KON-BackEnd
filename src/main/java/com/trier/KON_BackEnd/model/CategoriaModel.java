package com.trier.KON_BackEnd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "TBCATEGORIA")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class CategoriaModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDCATEGORIA")
    private Long cdCategoria;

    @Column(name = "NMCATEGORIA")
    private String nmCategoria;

    @Column(nullable = false)
    private Integer horaCategoria;

    @Column(name = "FLATIVO")
    private Boolean flAtivo;

}
