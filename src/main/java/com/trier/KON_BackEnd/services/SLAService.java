package com.trier.KON_BackEnd.services;

import com.trier.KON_BackEnd.dto.sla.SLARequestDto;
import com.trier.KON_BackEnd.dto.sla.response.SLAResponseDto;
import com.trier.KON_BackEnd.model.SLAModel;
import com.trier.KON_BackEnd.repository.SLARepository;
import lombok.AllArgsConstructor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@Service
public class SLAService {

    private final SLARepository slaRepository;

    @Transactional
    public SLAResponseDto criasSLA(SLARequestDto slaRequestDto){
        SLAModel sla = new SLAModel();
        sla.setCategoria(slaRequestDto.categoria());
        sla.setUsuario(slaRequestDto.usuario());
        sla.setQtHorasResposta(slaRequestDto.qtHorasResposta());
        sla.setQtHorasResolucao(slaRequestDto.qtHorasResolucao());

        SLAModel salvo = slaRepository.save(sla);

        return new SLAResponseDto(
            salvo.getCategoria(),
            salvo.getUsuario(),
            salvo.getQtHorasResposta(),
            salvo.getQtHorasResolucao()
        );
    }
}
