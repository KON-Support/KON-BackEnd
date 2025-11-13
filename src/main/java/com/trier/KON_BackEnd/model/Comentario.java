package com.trier.KON_BackEnd.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TBCOMENTARIO")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdComentario;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CDCHAMADO", nullable = false)
    private ChamadoModel cdChamado;

    @ManyToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JoinColumn(name = "CDUSUARIO", nullable = false)
    private UsuarioModel cdUsuarioModel;

    @Column(name = "DSCONTEUDO", nullable = false)
    private String dsConteudo;

    @Column(name = "HRCRIACAO", nullable = false)
    @CreationTimestamp
    private LocalTime hrCriacao;

    @Column(name = "DTCRIACAO", nullable = false)
    @CreatedDate
    private LocalDate dtCriacao;
}
