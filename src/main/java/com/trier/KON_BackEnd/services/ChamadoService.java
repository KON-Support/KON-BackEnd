package com.trier.KON_BackEnd.services;

import com.trier.KON_BackEnd.dto.request.ChamadoRequestDTO;
import com.trier.KON_BackEnd.dto.response.ChamadoResponseDTO;
import com.trier.KON_BackEnd.model.CategoriaModel;
import com.trier.KON_BackEnd.model.ChamadoModel;
import com.trier.KON_BackEnd.model.SLAModel;
import com.trier.KON_BackEnd.model.UsuarioModel;
import com.trier.KON_BackEnd.repository.CategoriaRepository;
import com.trier.KON_BackEnd.repository.ChamadoRepository;
import com.trier.KON_BackEnd.repository.SLARepository;
import com.trier.KON_BackEnd.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ChamadoService {

    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private SLARepository slaRepository;

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
                chamado.getAnexo(),
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

    @Transactional
    public ChamadoResponseDTO atribuirChamado(Long cdUsuario, Long cdChamado, Long cdCategoria, Long cdSLA) {

        ChamadoModel chamado = chamadoRepository.findById(cdChamado)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado!"));

        UsuarioModel usuario = usuarioRepository.findById(cdUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        CategoriaModel categoria = categoriaRepository.findById(cdCategoria)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));

        SLAModel sla = slaRepository.findById(cdSLA)
                .orElseThrow(() -> new RuntimeException("SLA não encontrado!"));

        chamado.setUsuario(usuario);
        chamado.setCategoria(categoria);
        chamado.setSla(sla);

        chamadoRepository.save(chamado);

        return new ChamadoResponseDTO(

                chamado.getCdChamado(),
                chamado.getDsTitulo(),
                chamado.getDsDescricao(),
                chamado.getStatus(),
                chamado.getUsuario(),
                chamado.getAnexo(),
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
