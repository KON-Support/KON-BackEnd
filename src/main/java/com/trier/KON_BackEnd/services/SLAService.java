package com.trier.KON_BackEnd.services;

import com.trier.KON_BackEnd.dto.sla.SLARequestDto;
import com.trier.KON_BackEnd.dto.sla.response.SLAResponseDto;
import com.trier.KON_BackEnd.model.SLAModel;
import com.trier.KON_BackEnd.repository.SLARepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class SLAService {

    private final SLARepository slaRepository;

    @Transactional
    public SLAResponseDto criasSLA(SLARequestDto slaRequestDto){
        SLAModel sla = new SLAModel();
        sla.setPrioridade(slaRequestDto.prioridade());
        sla.setQtHorasResposta(slaRequestDto.qtHorasResposta());
        sla.setDsHorasResolucao(slaRequestDto.dsHorasResposta());
        sla.setQtHorasResolucao(slaRequestDto.qtHorasResolucao());
        sla.setDsHorasResolucao(slaRequestDto.dsHorasResolucao());
        SLAModel salvo = slaRepository.save(sla);

        return new SLAResponseDto(
            salvo.getPrioridade(),
            salvo.getQtHorasResposta(),
            salvo.getDsHorasResposta(),
            salvo.getQtHorasResolucao(),
            salvo.getDsHorasResolucao()
        );
    }
}
