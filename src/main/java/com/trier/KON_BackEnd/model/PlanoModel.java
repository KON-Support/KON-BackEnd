package com.trier.KON_BackEnd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBPLANO")
public class PlanoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdPlano;

    @Column(nullable = false)
    private String nmPlano;

    @Column(nullable = false)
    private Double vlPlano;

    @Column(name = "HRRESPOSTAPLANO")
    private Integer hrRespostaPlano;

    @Column(name = "HRRESOLUCAOPLANO")
    private Integer hrResolucaoPlano;

    @Column(nullable = false)
    private Integer limiteUsuarios;
}
