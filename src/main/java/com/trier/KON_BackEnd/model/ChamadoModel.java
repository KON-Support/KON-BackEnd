package com.trier.KON_BackEnd.model;


import com.trier.KON_BackEnd.enums.Prioridade;
import com.trier.KON_BackEnd.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "PRIORIDADE")
    private Prioridade prioridade;

    /* @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "REPORTER", mappedBy = "CDUSUARIO")
    private Usuario usuario;
    */

    /* @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "CATEGORIA", mappedBy = "CDCATEGORIA")
    private Categoria categoria;
    */

    @CreationTimestamp
    @Column(name = "DTCRIACAO")
    private LocalDateTime dtCriacao;

    @Column(name = "DTATUALIZACAO")
    private LocalDateTime dtAtualizacao;

    @Column(name = "DTFECHAMENTO")
    private LocalDateTime dtFechamento;

    @Column(name = "DTVENCIMENTO")
    private LocalDateTime dtVencimento;

    @Column(name = "FLSLAVIOLADO")
    private Boolean flSlaViolado;
}
