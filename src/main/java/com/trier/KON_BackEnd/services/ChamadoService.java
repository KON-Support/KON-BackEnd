package com.trier.KON_BackEnd.services;

import ch.qos.logback.classic.Logger;
import com.trier.KON_BackEnd.dto.request.ChamadoRequestDTO;
import com.trier.KON_BackEnd.dto.response.ChamadoResponseDTO;
import com.trier.KON_BackEnd.enums.Status;
import com.trier.KON_BackEnd.exception.*;
import com.trier.KON_BackEnd.model.*;
import com.trier.KON_BackEnd.repository.*;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Autowired
    private PlanoRepository planoRepository;

    @Transactional
    public ChamadoResponseDTO abrirChamado(ChamadoRequestDTO chamadoRequest) {

        UsuarioModel solicitante = usuarioRepository.findById(chamadoRequest.solicitante())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(chamadoRequest.solicitante()));

        CategoriaModel categoria = categoriaRepository.findById(chamadoRequest.cdCategoria())
                .orElseThrow(() -> new CategoriaNaoEncontradoException(chamadoRequest.cdCategoria()));

        PlanoModel plano = planoRepository.findById(solicitante.getPlano().getCdPlano())
                .orElseThrow(() -> new PlanoNaoEncontradoException(chamadoRequest.cdPlano()));

        SLAModel sla = slaRepository.findByCategoriaCdCategoriaAndPlanoCdPlano(categoria.getCdCategoria(), plano.getCdPlano())
                .stream().findFirst()
                .orElseThrow(() -> new SLANaoEncontradoException("SLA não encontrado"));

        ChamadoModel chamado = new ChamadoModel();
        chamado.setDsTitulo(chamadoRequest.dsTitulo());
        chamado.setDsDescricao(chamadoRequest.dsDescricao());
        chamado.setStatus(chamadoRequest.status());
        chamado.setCategoria(categoria);
        chamado.setSolicitante(solicitante);
        chamado.setFlSlaViolado(false);
        chamado.setDtCriacao(LocalDateTime.now());
        chamado.setPlano(plano);
        chamado.setDtVencimento(chamado.getDtCriacao().plusHours(sla.getQtHorasResposta()));

        if (chamadoRequest.responsavel() != null) {
            UsuarioModel responsavel = usuarioRepository.findById(chamadoRequest.responsavel())
                    .orElseThrow(() -> new UsuarioNaoEncontradoException(chamadoRequest.responsavel()));
            chamado.setResponsavel(responsavel);
        }

        chamadoRepository.save(chamado);
        return convertToResponseDTO(chamado);
    }

    @Transactional
    public ChamadoResponseDTO atribuirChamado(Long cdChamado, Long responsavel, Long cdCategoria, Long cdSLA) {

        ChamadoModel chamado = chamadoRepository.findByIdWithRelations(cdChamado)
                .orElseThrow(() -> new ChamadoNaoEncontradoException(cdChamado));

        if (responsavel != null) {
            UsuarioModel cdResponsavel = usuarioRepository.findById(responsavel)
                    .orElseThrow(() -> new UsuarioNaoEncontradoException(responsavel));
            chamado.setResponsavel(cdResponsavel);
        }

        if (cdCategoria != null) {
            CategoriaModel categoria = categoriaRepository.findById(cdCategoria)
                    .orElseThrow(() -> new CategoriaNaoEncontradoException(cdCategoria));
            chamado.setCategoria(categoria);
        }

        if (cdSLA != null) {
            SLAModel sla = slaRepository.findById(cdSLA)
                    .orElseThrow(() -> new RuntimeException("SLA não encontrado!"));
            chamado.setSla(sla);

            if (sla.getQtHorasResolucao() != null && chamado.getDtCriacao() != null) {
                chamado.setDtVencimento(chamado.getDtCriacao().plusHours(sla.getQtHorasResolucao()));
            }
        }

        chamado.setStatus(Status.EM_ANDAMENTO);
        chamadoRepository.save(chamado);

        return convertToResponseDTO(chamado);
    }

    @Transactional
    public ChamadoResponseDTO atualizarStatus(Long cdChamado, Status status) {

        ChamadoModel chamado = chamadoRepository.findByIdWithRelations(cdChamado)
                .orElseThrow(() -> new ChamadoNaoEncontradoException(cdChamado));

        if (Status.FECHADO.equals(chamado.getStatus())) {
            throw new IllegalStateException("Não é possível alterar o status de um chamado já fechado!");
        }

        chamado.setStatus(status);
        chamadoRepository.save(chamado);

        return convertToResponseDTO(chamado);
    }

    @Transactional
    public ChamadoResponseDTO fecharChamado(Long cdChamado, Status status) {

        ChamadoModel chamado = chamadoRepository.findByIdWithRelations(cdChamado)
                .orElseThrow(() -> new ChamadoNaoEncontradoException(cdChamado));

        if (Status.FECHADO.equals(chamado.getStatus())) {
            throw new IllegalStateException("O chamado já está fechado");
        }

        if (status != Status.RESOLVIDO && status != Status.FECHADO) {
            throw new IllegalArgumentException("Para fechar um chamado, o status deve ser RESOLVIDO ou FECHADO");
        }

        chamado.setStatus(status);
        LocalDateTime agora = LocalDateTime.now();
        chamado.setDtFechamento(agora);

        if (chamado.getDtVencimento() != null) {
            chamado.setDtVencimento(agora);

            if (agora.isAfter(chamado.getDtVencimento())) {
                chamado.setFlSlaViolado(true);
            }
        }

        chamadoRepository.save(chamado);

        return convertToResponseDTO(chamado);
    }

    @Transactional
    public ChamadoResponseDTO adicionarAnexo(Long cdChamado, Long cdAnexo) {

        ChamadoModel chamado = chamadoRepository.findByIdWithRelations(cdChamado)
                .orElseThrow(() -> new ChamadoNaoEncontradoException(cdChamado));

        AnexoModel anexo = anexoRepository.findById(cdAnexo)
                .orElseThrow(() -> new RuntimeException("Anexo não encontrado!"));

        chamado.setAnexo(anexo);
        chamadoRepository.save(chamado);

        return convertToResponseDTO(chamado);
    }

    @Transactional
    public List<ChamadoResponseDTO> listarTodosChamados() {
        List<ChamadoModel> chamados = chamadoRepository.findAll();
        return chamados.stream().map(this::convertToResponseDTO).toList();
    }

    @Transactional
    public ChamadoResponseDTO listarChamado(Long cdChamado) {
        ChamadoModel chamado = chamadoRepository.findByIdWithRelations(cdChamado)
                .orElseThrow(() -> new ChamadoNaoEncontradoException(cdChamado));

        return convertToResponseDTO(chamado);
    }

    @Transactional
    public List<ChamadoResponseDTO> listarPorStatus(Status status) {
        List<ChamadoModel> chamados = chamadoRepository.findAllByStatus(status);
        return chamados.stream().map(this::convertToResponseDTO).toList();
    }

    @Transactional
    public List<ChamadoResponseDTO> listarPorSolicitante(Long cdUsuario) {
        List<ChamadoModel> chamados = chamadoRepository.findAllBySolicitante_CdUsuario(cdUsuario);
        return chamados.stream().map(this::convertToResponseDTO).toList();
    }

    @Transactional
    public List<ChamadoResponseDTO> listarPorResponsavel(Long cdUsuario) {
        List<ChamadoModel> chamados = chamadoRepository.findAllByResponsavel_CdUsuario(cdUsuario);
        return chamados.stream().map(this::convertToResponseDTO).toList();
    }

    @Transactional
    public List<ChamadoResponseDTO> listarNaoAtribuidos() {
        List<ChamadoModel> chamados = chamadoRepository.findAllByResponsavelIsNull();
        return chamados.stream().map(this::convertToResponseDTO).toList();
    }

    private ChamadoResponseDTO convertToResponseDTO(ChamadoModel chamado) {

        ChamadoResponseDTO.UsuarioSimplesDTO solicitanteDTO = null;
        if (chamado.getSolicitante() != null) {
            solicitanteDTO = new ChamadoResponseDTO.UsuarioSimplesDTO(
                    chamado.getSolicitante().getCdUsuario(),
                    chamado.getSolicitante().getNmUsuario()
            );
        }

        ChamadoResponseDTO.UsuarioSimplesDTO responsavelDTO = null;
        if (chamado.getResponsavel() != null) {
            responsavelDTO = new ChamadoResponseDTO.UsuarioSimplesDTO(
                    chamado.getResponsavel().getCdUsuario(),
                    chamado.getResponsavel().getNmUsuario()
            );
        }

        ChamadoResponseDTO.AnexoSimplesDTO anexoDTO = null;
        if (chamado.getAnexo() != null) {
            anexoDTO = new ChamadoResponseDTO.AnexoSimplesDTO(
                    chamado.getAnexo().getCdAnexo(),
                    chamado.getAnexo().getNmArquivo(),
                    chamado.getAnexo().getDsTipoArquivo()
            );
        }

        ChamadoResponseDTO.CategoriaSimplesDTO categoriaDTO = null;
        if (chamado.getCategoria() != null) {
            categoriaDTO = new ChamadoResponseDTO.CategoriaSimplesDTO(
                    chamado.getCategoria().getCdCategoria(),
                    chamado.getCategoria().getNmCategoria()
            );
        }

        ChamadoResponseDTO.SLASimplesDTO slaDTO = null;
        if (chamado.getSla() != null) {
            slaDTO = new ChamadoResponseDTO.SLASimplesDTO(
                    chamado.getSla().getCdSLA(),
                    chamado.getSla().getQtHorasResposta(),
                    chamado.getSla().getQtHorasResolucao()
            );
        }

        return new ChamadoResponseDTO(
                chamado.getCdChamado(),
                chamado.getDsTitulo(),
                chamado.getDsDescricao(),
                chamado.getStatus(),
                solicitanteDTO,
                responsavelDTO,
                anexoDTO,
                categoriaDTO,
                slaDTO,
                chamado.getDtCriacao(),
                chamado.getDtFechamento(),
                chamado.getDtVencimento(),
                chamado.getFlSlaViolado()
        );
    }
}