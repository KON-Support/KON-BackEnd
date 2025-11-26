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
public class ComentarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdComentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CDCHAMADO", nullable = false)
    private ChamadoModel chamado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CDUSUARIO", nullable = false)
    private UsuarioModel usuario;

    @OneToOne
    @JoinColumn(name = "CDANEXO")
    private AnexoModel anexo;

    @Column(nullable = false)
    private String dsConteudo;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalTime hrCriacao;

    @Column(nullable = false)
    @CreatedDate
    private LocalDate dtCriacao;
}
