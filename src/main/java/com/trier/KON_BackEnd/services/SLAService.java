package com.trier.KON_BackEnd.services;

import com.trier.KON_BackEnd.dto.sla.SLARequestDto;
import com.trier.KON_BackEnd.dto.sla.SLAResponseListar;
import com.trier.KON_BackEnd.dto.sla.response.SLAResponseDto;
import com.trier.KON_BackEnd.exception.SLANaoEncontradoException;
import com.trier.KON_BackEnd.model.*;
import com.trier.KON_BackEnd.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class SLAService {

    private final SLARepository slaRepository;
    private final CategoriaRepository catRepository;
    private final PlanoRepository planoRepository;
    private final ChamadoRepository chamadoRepository;

    @Transactional
    public SLAResponseDto criaSLA(SLARequestDto slaRequestDto) {

        CategoriaModel cat = catRepository.findById(slaRequestDto.cdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        PlanoModel plano = planoRepository.findById(slaRequestDto.cdPlano())
                .orElseThrow(() -> new RuntimeException("Plano não encontrado"));


        SLAModel sla = new SLAModel();
        sla.setCategoria(cat);
        sla.setPlano(plano);
        sla.setQtHorasResposta(cat.getHrResposta() + plano.getHrRespostaPlano());
        sla.setQtHorasResolucao(cat.getHrResolucao() + plano.getHrResolucaoPlano());

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

    @Transactional
    public List<SLAResponseDto> buscarCategoriaPlano(Long cdCategoria, Long cdPlano) {
        CategoriaModel categoria = catRepository.findById(cdCategoria)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada: " + cdCategoria));

        PlanoModel plano = planoRepository.findById(cdPlano)
                .orElseThrow(() -> new RuntimeException("Plano não encontrado: " + cdPlano));

        List<SLAModel> sla = slaRepository.findByCategoriaCdCategoriaAndPlanoCdPlano(cdCategoria, cdPlano);

        return sla.stream()
                .map(slas -> new SLAResponseDto(
                        slas.getCdSLA(),
                        slas.getPlano().getNmPlano(),
                        slas.getCategoria().getNmCategoria(),
                        slas.getQtHorasResposta(),
                        slas.getQtHorasResolucao()
                )).collect(Collectors.toList());
    }

}
