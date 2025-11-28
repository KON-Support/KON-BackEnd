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
@Table(name = "TBANEXO")
public class AnexoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdAnexo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CDCHAMADO", nullable = false)
    private ChamadoModel chamado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CDUSUARIO", nullable = false)
    private UsuarioModel usuario;

    @Column(nullable = false)
    private String nmArquivo;

    @Column(nullable = false)
    private String dsTipoArquivo;

    @Column(nullable = false)
    @CreatedDate
    private LocalDate dtUpload;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalTime hrUpload;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private byte[] arquivo;
}
