package com.trier.KON_BackEnd.model;

import com.trier.KON_BackEnd.enums.Prioridade;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.datetime.DateFormatter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBSLA")
public class SLAModel {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    Long cdSLA;

    Prioridade prioridade;

    LocalDateTime qtHorasResposta = LocalDateTime.now();

    LocalDateTime qtHorasResolucao = LocalDateTime.now();

    Boolean flAtivo;

    DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    String horasRepostaFormatada = qtHorasResposta.format(formatador);
    String horasResolucaoFormatada = qtHorasResolucao.format(formatador);

}

