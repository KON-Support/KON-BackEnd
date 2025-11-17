package com.trier.KON_BackEnd.model;

import com.trier.KON_BackEnd.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tb_chamados")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ChamadoModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDCHAMADO")
    private Long cdChamado;

    @Column(name = "DSTITULO")
    private String dsTitulo;

    @Column(name = "DSDESCRICAO")
    private String dsDescricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO", referencedColumnName = "CDUSUARIO")
    private UsuarioModel usuario;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ANEXO", referencedColumnName = "CDANEXO")
    private AnexoModel anexo;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORIA", referencedColumnName = "CDCATEGORIA")
    private CategoriaModel categoria;

    @CreatedDate
    @Column(name = "DTCRIACAO")
    private LocalDate dtCriacao;

    @CreationTimestamp
    @Column(name = "HRCRIACAO")
    private LocalTime hrCriacao;

    @Column(name = "DTFECHAMENTO")
    private LocalDate dtFechamento;

    @Column(name = "HRFECHAMENTO")
    private LocalTime hrFechamento;

    @Column(name = "DTVENCIMENTO")
    private LocalDate dtVencimento;

    @Column(name = "HRVENCIMENTO")
    private LocalTime hrVencimento;

    @Column(name = "FLSLAVIOLADO")
    private Boolean flSlaViolado;
}
