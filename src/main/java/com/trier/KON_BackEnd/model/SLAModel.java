package com.trier.KON_BackEnd.model;

import com.trier.KON_BackEnd.enums.Prioridade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.datetime.DateFormatter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBSLA")
public class SLAModel {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cdSLA;

    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;

    private LocalDateTime qtHorasResposta = LocalDateTime.now();

    private LocalDateTime qtHorasResolucao = LocalDateTime.now();

    private String flAtivo = "S";
}

