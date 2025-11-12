package com.trier.KON_BackEnd.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TBCOMENTARIO")
public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdComentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CDCHAMADO", nullable = false)
    private Chamado cdChamado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CDUSUARIO", nullable = false)
    private Usuario cdUsuario;

    @Column(name = "DSCONTEUDO", nullable = false)
    private String dsConteudo;

    @Column(name = "DTCRIACAO", nullable = false)
    @CreationTimestamp
    private LocalDateTime dtCriacao;
}
