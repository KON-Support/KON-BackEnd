package com.trier.KON_BackEnd.services;

import com.trier.KON_BackEnd.dto.request.ChamadoRequestDTO;
import com.trier.KON_BackEnd.dto.response.ChamadoResponseDTO;
import com.trier.KON_BackEnd.enums.Status;
import com.trier.KON_BackEnd.exception.ChamadoNaoEncontradoException;
import com.trier.KON_BackEnd.exception.UsuarioNaoEncontradoException;
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

        CategoriaModel categoria = categoriaRepository.findById(chamadoRequest.cdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));

        ChamadoModel chamado = new ChamadoModel();
        chamado.setDsTitulo(chamadoRequest.dsTitulo());
        chamado.setDsDescricao(chamadoRequest.dsDescricao());
        chamado.setStatus(chamadoRequest.status());
        chamado.setCategoria(categoria);
        chamado.setFlSlaViolado(false);

        chamado.setDtCriacao(LocalDate.now());
        chamado.setHrCriacao(LocalTime.now());

        // Se foi informado usuário no request (verificar com equipe)...
        /* if (chamadoRequest.cdUsuario() != null) {
            UsuarioModel usuario = usuarioRepository.findById(chamadoRequest.cdUsuario())
                    .orElseThrow(() -> new UsuarioNaoEncontradoException(chamadoRequest.cdUsuario()));
            chamado.setUsuario(usuario);
        } */

        // Se foi informado SLA no request (verificar com equipe)...
        /* if (chamadoRequest.cdSLA() != null) {
            SLAModel sla = slaRepository.findById(chamadoRequest.cdSLA())
                    .orElseThrow(() -> new RuntimeException("SLA não encontrado!"));
            chamado.setSla(sla);

            // Calcula data/hora de vencimento baseado no SLA (verificar com equipe)...
            if (sla.getQtHorasResolucao() != null) {
                LocalTime hrVencimento = chamado.getHrCriacao().plusHours(sla.getQtHorasResolucao());
                LocalDate dtVencimento = chamado.getDtCriacao();

                // Se passar de 24h, ajusta o dia (verificar com equipe)...
                if (hrVencimento.isBefore(chamado.getHrCriacao())) {
                    dtVencimento = dtVencimento.plusDays(sla.getQtHorasResolucao() / 24);
                }

                chamado.setDtVencimento(dtVencimento);
                chamado.setHrVencimento(hrVencimento);
            }
        } */

        chamadoRepository.save(chamado);
        return convertToResponseDTO(chamado);
    }

    @Transactional
    public ChamadoResponseDTO atribuirChamado(Long cdUsuario, Long cdChamado, Long cdCategoria, Long cdSLA) {

        ChamadoModel chamado = chamadoRepository.findById(cdChamado)
                .orElseThrow(() -> new ChamadoNaoEncontradoException(cdChamado));

        if (cdUsuario != null) {
            UsuarioModel usuario = usuarioRepository.findById(cdUsuario)
                    .orElseThrow(() -> new UsuarioNaoEncontradoException(cdUsuario));
            chamado.setUsuario(usuario);
        }

        if (cdCategoria != null) {
            CategoriaModel categoria = categoriaRepository.findById(cdCategoria)
                    .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));
            chamado.setCategoria(categoria);
        }

        if (cdSLA != null) {
            SLAModel sla = slaRepository.findById(cdSLA)
                    .orElseThrow(() -> new RuntimeException("SLA não encontrado!"));
            chamado.setSla(sla);

            if (sla.getQtHorasResolucao() != null && chamado.getDtCriacao() != null) {
                LocalTime hrVencimento = chamado.getHrCriacao().plusHours(sla.getQtHorasResolucao());
                LocalDate dtVencimento = chamado.getDtCriacao();

                if (hrVencimento.isBefore(chamado.getHrCriacao())) {
                    dtVencimento = dtVencimento.plusDays(sla.getQtHorasResolucao() / 24);
                }

                chamado.setDtVencimento(dtVencimento);
                chamado.setHrVencimento(hrVencimento);

            }
        }

        chamadoRepository.save(chamado);

        return convertToResponseDTO(chamado);

    }

    @Transactional
    public ChamadoResponseDTO atualizarStatus(Long cdChamado, Status status) {

        ChamadoModel chamado = chamadoRepository.findById(cdChamado)
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

        ChamadoModel chamado = chamadoRepository.findById(cdChamado)
                .orElseThrow(() -> new ChamadoNaoEncontradoException(cdChamado));

        if (Status.FECHADO.equals(chamado.getStatus())) {
            throw new IllegalStateException("O chamado já está fechado");
        }

        chamado.setStatus(status);
        chamado.setDtFechamento(LocalDate.now());
        chamado.setHrFechamento(LocalTime.now());

        if (chamado.getDtVencimento() != null && chamado.getHrVencimento() != null) {
            LocalDate hoje = LocalDate.now();
            LocalTime agora = LocalTime.now();

            if (hoje.isAfter(chamado.getDtVencimento()) ||
                    (hoje.isEqual(chamado.getDtVencimento()) && agora.isAfter(chamado.getHrVencimento()))) {
                chamado.setFlSlaViolado(true);
            }
        }

        chamadoRepository.save(chamado);

        return convertToResponseDTO(chamado);

    }

    @Transactional
    public ChamadoResponseDTO adicionarAnexo(Long cdChamado, Long cdAnexo) {

        ChamadoModel chamado = chamadoRepository.findById(cdChamado)
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

        ChamadoModel chamado = chamadoRepository.findById(cdChamado)
                .orElseThrow(() -> new ChamadoNaoEncontradoException(cdChamado));

        return convertToResponseDTO(chamado);

    }

    @Transactional
    public List<ChamadoResponseDTO> listarPorStatus(Status status) {

        List<ChamadoModel> chamados = chamadoRepository.findAllByStatus(status);

        return chamados.stream().map(this::convertToResponseDTO).toList();

    }

    private ChamadoResponseDTO convertToResponseDTO(ChamadoModel chamado) {

        ChamadoResponseDTO.UsuarioSimplesDTO usuarioDTO = null;
        if (chamado.getUsuario() != null) {
            usuarioDTO = new ChamadoResponseDTO.UsuarioSimplesDTO(
                    chamado.getUsuario().getCdUsuario(),
                    chamado.getUsuario().getNmUsuario()
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
                usuarioDTO,
                anexoDTO,
                categoriaDTO,
                slaDTO,
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