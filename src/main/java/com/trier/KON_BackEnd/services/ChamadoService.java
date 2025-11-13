package com.trier.KON_BackEnd.services;

import com.trier.KON_BackEnd.dto.request.ChamadoRequestDTO;
import com.trier.KON_BackEnd.dto.response.ChamadoResponseDTO;
import com.trier.KON_BackEnd.model.ChamadoModel;
import com.trier.KON_BackEnd.repository.ChamadoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Transactional
    public ChamadoResponseDTO abrirChamado(ChamadoRequestDTO chamadoRequest) {

        ChamadoModel chamado = new ChamadoModel();

        chamado.setDsTitulo(chamadoRequest.dsTitulo());
        chamado.setDsDescricao(chamadoRequest.dsDescricao());
        chamado.setStatus(chamadoRequest.status());
        chamado.setCategoria(chamadoRequest.nmCategoria());
        chamado.setFlSlaViolado(chamadoRequest.flSlaViolado());

        chamadoRepository.save(chamado);

        return new ChamadoResponseDTO(

                chamado.getCdChamado(),
                chamado.getDsTitulo(),
                chamado.getDsDescricao(),
                chamado.getStatus(),
                chamado.getUsuario(),
                /*chamado.getAnexo(),*/
                chamado.getCategoria(),
                chamado.getDtCriacao(),
                chamado.getHrCriacao(),
                chamado.getDtFechamento(),
                chamado.getHrFechamento(),
                chamado.getDtVencimento(),
                chamado.getHrVencimento(),
                chamado.getFlSlaViolado()

        );

    }

}
