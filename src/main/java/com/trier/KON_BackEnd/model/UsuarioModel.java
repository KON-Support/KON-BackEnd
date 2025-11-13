package com.trier.KON_BackEnd.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "TBUSUARIO")
public class UsuarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdUsuario;

    @Column(nullable = false)
    private String nmUsuario;

    @Column(nullable = false)
    private String dsSenha;

    @Column(unique = true, nullable = false)
    private String dsEmail;

    @CreationTimestamp
    private LocalDateTime dtCriacao;

    @LastModifiedDate
    private LocalDateTime dtUltimoAcesso;

    private boolean flAtivo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TBUSUARIOROLES",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleModel> roleModel = new HashSet<>();

}
