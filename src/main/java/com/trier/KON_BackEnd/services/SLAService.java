package com.trier.KON_BackEnd.services;

import com.trier.KON_BackEnd.dto.sla.SLARequestDto;
import com.trier.KON_BackEnd.dto.sla.SLAResponseListar;
import com.trier.KON_BackEnd.dto.sla.response.SLAResponseDto;
import com.trier.KON_BackEnd.exception.SLANaoEncontradoException;
import com.trier.KON_BackEnd.exception.UsuarioNaoEncontradoException;
import com.trier.KON_BackEnd.model.CategoriaModel;
import com.trier.KON_BackEnd.model.SLAModel;
import com.trier.KON_BackEnd.model.UsuarioModel;
import com.trier.KON_BackEnd.repository.CategoriaRepository;
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
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public SLAResponseDto criasSLA(SLARequestDto slaRequestDto) {

        CategoriaModel cat = catRepository.findById(slaRequestDto.cdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        UsuarioModel usuario = usuarioRepository.findById(slaRequestDto.cdUsuario())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(slaRequestDto.cdUsuario()));

        SLAModel sla = new SLAModel();
        sla.setCategoria(cat);
        sla.setUsuario(usuario);
        sla.setQtHorasResposta(slaRequestDto.qtHorasResposta());
        sla.setQtHorasResolucao(slaRequestDto.qtHorasResolucao());

        SLAModel salvo = slaRepository.save(sla);

        return new SLAResponseDto(
                salvo.getCdSLA(),
                salvo.getCategoria().getCdCategoria(),
                salvo.getUsuario().getCdUsuario(),
                salvo.getQtHorasResposta(),
                salvo.getQtHorasResolucao()
        );
    }

    @Transactional
    public SLAResponseDto autalizarSLA(SLARequestDto requestDto, Long cdSLA) {
        SLAModel sla = slaRepository.findByCdSLA(cdSLA)
                .orElseThrow(() -> new SLANaoEncontradoException("SLA não encontrado: " + cdSLA));

        CategoriaModel cat = catRepository.findById(requestDto.cdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        UsuarioModel usuario = usuarioRepository.findById(requestDto.cdUsuario())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(requestDto.cdUsuario()));

        sla.setCategoria(cat);
        sla.setUsuario(usuario);
        sla.setQtHorasResposta(requestDto.qtHorasResposta());
        sla.setQtHorasResolucao(requestDto.qtHorasResolucao());

        SLAModel atualizado = slaRepository.save(sla);

        return new SLAResponseDto(
                atualizado.getCdSLA(),
                atualizado.getCategoria().getCdCategoria(),
                atualizado.getUsuario().getCdUsuario(),
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
                        sla.getUsuario().getNmUsuario(),
                        sla.getQtHorasResposta(),
                        sla.getQtHorasResolucao()
                ))
                .collect(Collectors.toList());
    }
}
