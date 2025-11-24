package com.trier.KON_BackEnd.model;

import com.trier.KON_BackEnd.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "tb_chamados")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChamadoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CDCHAMADO")
    private Long cdChamado;

    @Column(name = "DSTITULO", nullable = false)
    private String dsTitulo;

    @Column(name = "DSDESCRICAO", nullable = false)
    private String dsDescricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SOLICITANTE", referencedColumnName = "CDUSUARIO")
    private UsuarioModel solicitante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RESPONSAVEL", referencedColumnName = "CDUSUARIO")
    private UsuarioModel responsavel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ANEXO", referencedColumnName = "CDANEXO")
    private AnexoModel anexo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORIA", referencedColumnName = "CDCATEGORIA", nullable = false)
    private CategoriaModel categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SLA", referencedColumnName = "CDSLA")
    private SLAModel sla;

    @Column(name = "DTCRIACAO", nullable = false)
    private LocalDate dtCriacao;

    @Column(name = "HRCRIACAO", nullable = false)
    private LocalTime hrCriacao;

    @Column(name = "DTFECHAMENTO")
    private LocalDate dtFechamento;

    @Column(name = "HRFECHAMENTO")
    private LocalTime hrFechamento;

    @Column(name = "DTVENCIMENTO")
    private LocalDate dtVencimento;

    @Column(name = "HRVENCIMENTO")
    private LocalTime hrVencimento;

    @Column(name = "FLSLAVIOLADO", nullable = false)
    private Boolean flSlaViolado = false;

    @PrePersist
    protected void onCreate() {
        if (dtCriacao == null) {
            dtCriacao = LocalDate.now();
        }
        if (hrCriacao == null) {
            hrCriacao = LocalTime.now();
        }
        if (flSlaViolado == null) {
            flSlaViolado = false;
        }
    }
}