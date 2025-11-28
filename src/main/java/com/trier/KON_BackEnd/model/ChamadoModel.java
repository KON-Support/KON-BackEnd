package com.trier.KON_BackEnd.model;

import com.trier.KON_BackEnd.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

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
    private LocalDateTime dtCriacao;

    @Column(name = "DTFECHAMENTO")
    private LocalDateTime dtFechamento;

    @Column(name = "DTVENCIMENTO")
    private LocalDateTime dtVencimento;

    @Column(name = "FLSLAVIOLADO", nullable = false)
    private Boolean flSlaViolado = false;

    @PrePersist
    protected void onCreate() {
        if (dtCriacao == null) {
            dtCriacao = LocalDateTime.now();
        }
        if (flSlaViolado == null) {
            flSlaViolado = false;
        }
    }
}