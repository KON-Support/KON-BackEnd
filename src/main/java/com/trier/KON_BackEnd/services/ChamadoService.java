package com.trier.KON_BackEnd.services;

import com.trier.KON_BackEnd.dto.request.ChamadoRequestDTO;
import com.trier.KON_BackEnd.dto.response.ChamadoResponseDTO;
import com.trier.KON_BackEnd.enums.Status;
import com.trier.KON_BackEnd.model.*;
import com.trier.KON_BackEnd.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    @Autowired
    private AnexoRepository anexoRepository;

    @Transactional
    public ChamadoResponseDTO abrirChamado(ChamadoRequestDTO chamadoRequest) {

        ChamadoModel chamado = new ChamadoModel();

        chamado.setDsTitulo(chamadoRequest.dsTitulo());
        chamado.setDsDescricao(chamadoRequest.dsDescricao());
        chamado.setStatus(chamadoRequest.status());
        chamado.setCategoria(chamadoRequest.categoria());
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

    @Transactional
    public ChamadoResponseDTO atualizarStatus(Long cdChamado, Status status) {

        ChamadoModel chamado = chamadoRepository.findById(cdChamado)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado!"));

        chamado.setStatus(status);

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
    public ChamadoResponseDTO fecharChamado(Long cdChamado, Status status) {

        ChamadoModel chamado = chamadoRepository.findById(cdChamado)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));

        if ("FECHADO".equals(chamado.getStatus())) {
            throw new IllegalStateException("O chamado já está fechado");
        }

        chamado.setStatus(status);
        chamado.setDtFechamento(LocalDate.now());
        chamado.setHrFechamento(LocalTime.now());

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
    public ChamadoResponseDTO adicionarComentario(Long cdChamado) {

        ChamadoModel chamado = chamadoRepository.findById(cdChamado)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado"));

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
    public ChamadoResponseDTO adicionarAnexo(Long cdChamado, Long cdAnexo) {

        ChamadoModel chamado = chamadoRepository.findById(cdChamado)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado!"));

        AnexoModel anexo = anexoRepository.findById(cdAnexo)
                .orElseThrow(() -> new RuntimeException("Anexo não encontrado!"));

        chamado.setAnexo(anexo);

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
    public List<ChamadoResponseDTO> listarTodosChamados() {

        List<ChamadoModel> chamado = chamadoRepository.findAll();

        return chamado.stream().map(this:: convertToResponseDTO).toList();

    }

    @Transactional
    public ChamadoResponseDTO listarChamado(Long cdChamado) {

        ChamadoModel chamado = chamadoRepository.findById(cdChamado)
                .orElseThrow(() -> new RuntimeException("Chamado não encontrado!"));

        return convertToResponseDTO(chamado);

    }

    @Transactional
    public List<ChamadoResponseDTO> listarPorStatus(Status status) {

        List<ChamadoModel> chamado = chamadoRepository.findAllByStatus(status);

        return chamado.stream().map(this:: convertToResponseDTO).toList();

    }

    private ChamadoResponseDTO convertToResponseDTO(ChamadoModel chamado) {
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
