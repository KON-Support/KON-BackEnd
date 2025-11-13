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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CDCHAMADO", nullable = false)
    private ChamadoModel cdChamado;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "CDUSUARIO", nullable = false)
    private UsuarioModel cdUsuario;

    @Column(name = "NMARQUIVO", nullable = false)
    private String nmArquivo;

    @Column(name = "DSTIPOARQUIVO", nullable = false)
    private String dsTipoArquivo;

    @Column(name = "DTUPLOAD", nullable = false)
    @CreatedDate
    private LocalDate dtUpload;

    @Column(name = "HRCRIACAO", nullable = false)
    @CreationTimestamp
    private LocalTime hrUpload;

    @Lob
    @Column(name = "ARQUIVO", nullable = false)
    private byte[] arquivo;
}
