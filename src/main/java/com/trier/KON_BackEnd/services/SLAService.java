package com.trier.KON_BackEnd.services;

import com.trier.KON_BackEnd.dto.sla.SLARequestDto;
import com.trier.KON_BackEnd.dto.sla.SLAResponseListar;
import com.trier.KON_BackEnd.dto.sla.response.SLAResponseDto;
import com.trier.KON_BackEnd.exception.SLANaoEncontradoException;
import com.trier.KON_BackEnd.exception.UsuarioNaoEncontradoException;
import com.trier.KON_BackEnd.model.CategoriaModel;
import com.trier.KON_BackEnd.model.PlanoModel;
import com.trier.KON_BackEnd.model.SLAModel;
import com.trier.KON_BackEnd.model.UsuarioModel;
import com.trier.KON_BackEnd.repository.CategoriaRepository;
import com.trier.KON_BackEnd.repository.PlanoRepository;
import com.trier.KON_BackEnd.repository.SLARepository;
import com.trier.KON_BackEnd.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SLAService {

    private final SLARepository slaRepository;
    private final CategoriaRepository catRepository;
    private final PlanoRepository planoRepository;

    @Transactional
    public SLAResponseDto criasSLA(SLARequestDto slaRequestDto) {

        CategoriaModel cat = catRepository.findById(slaRequestDto.cdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        PlanoModel plano = planoRepository.findById(slaRequestDto.cdPlano())
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));

        SLAModel sla = new SLAModel();
        sla.setCategoria(cat);
        sla.setPlano(plano);
        sla.setQtHorasResposta(slaRequestDto.qtHorasResposta());
        sla.setQtHorasResolucao(slaRequestDto.qtHorasResolucao());

        SLAModel salvo = slaRepository.save(sla);

        return new SLAResponseDto(
                salvo.getCdSLA(),
                salvo.getCategoria().getNmCategoria(),
                salvo.getPlano().getNmPlano(),
                salvo.getQtHorasResposta(),
                salvo.getQtHorasResolucao()
        );
    }

    @Transactional
    public SLAResponseDto atualizarSLA(SLARequestDto requestDto, Long cdSLA) {
        SLAModel sla = slaRepository.findByCdSLA(cdSLA)
                .orElseThrow(() -> new SLANaoEncontradoException("SLA não encontrado: " + cdSLA));

        CategoriaModel cat = catRepository.findById(requestDto.cdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        PlanoModel plano = planoRepository.findById(requestDto.cdPlano())
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));

        sla.setCategoria(cat);
        sla.setPlano(plano);
        sla.setQtHorasResposta(requestDto.qtHorasResposta());
        sla.setQtHorasResolucao(requestDto.qtHorasResolucao());

        SLAModel atualizado = slaRepository.save(sla);

        return new SLAResponseDto(
                atualizado.getCdSLA(),
                atualizado.getCategoria().getNmCategoria(),
                atualizado.getPlano().getNmPlano(),
                atualizado.getQtHorasResposta(),
                atualizado.getQtHorasResolucao()
        );
    }

    @Transactional
    public List<SLAResponseListar> listarSLA() {
        return slaRepository.findAll()
                .stream()
                .map(sla -> new SLAResponseListar(
                        sla.getCdSLA(),
                        sla.getCategoria().getNmCategoria(),
                        sla.getPlano().getNmPlano(),
                        sla.getQtHorasResposta(),
                        sla.getQtHorasResolucao()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<SLAResponseListar> buscarPorCategoria(Long cdCategoria) {
        CategoriaModel categoria = catRepository.findById(cdCategoria)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada: " + cdCategoria));

        List<SLAModel> sla = slaRepository.findByCategoriaCdCategoria(cdCategoria);
        if (sla.isEmpty()) {
            throw new SLANaoEncontradoException("Nenhum SLA encontrado para a categoria: " + categoria.getNmCategoria());
        }
        return sla.stream()
                .map(slas -> new SLAResponseListar(
                        slas.getCdSLA(),
                        slas.getCategoria().getNmCategoria(),
                        slas.getPlano().getNmPlano(),
                        slas.getQtHorasResposta(),
                        slas.getQtHorasResolucao()
                ))
                .collect(Collectors.toList());
    }

}
