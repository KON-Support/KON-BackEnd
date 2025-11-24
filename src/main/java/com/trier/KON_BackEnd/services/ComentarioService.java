package com.trier.KON_BackEnd.services;

import com.trier.KON_BackEnd.dto.request.ComentarioAnexoRequestDTO;
import com.trier.KON_BackEnd.dto.request.ComentarioRequestDTO;
import com.trier.KON_BackEnd.dto.response.ComentarioResponseDTO;
import com.trier.KON_BackEnd.exception.ChamadoNaoEncontradoException;
import com.trier.KON_BackEnd.exception.UsuarioNaoEncontradoException;
import com.trier.KON_BackEnd.model.AnexoModel;
import com.trier.KON_BackEnd.model.ComentarioModel;
import com.trier.KON_BackEnd.repository.AnexoRepository;
import com.trier.KON_BackEnd.repository.ChamadoRepository;
import com.trier.KON_BackEnd.repository.ComentarioRepository;
import com.trier.KON_BackEnd.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ChamadoRepository chamadoRepository;
    @Autowired
    private AnexoRepository anexoRepository;

    @Transactional
    public ComentarioResponseDTO criarComentario(ComentarioRequestDTO comentarioRequest) {
        var usuario = usuarioRepository.findById(comentarioRequest.cdUsuario())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(comentarioRequest.cdUsuario()));

        var chamado = chamadoRepository.findById(comentarioRequest.cdChamado())
                .orElseThrow(() -> new ChamadoNaoEncontradoException(comentarioRequest.cdChamado()));

        ComentarioModel comentarioModel = new ComentarioModel();

        comentarioModel.setUsuario(usuario);
        comentarioModel.setChamado(chamado);
        comentarioModel.setDsConteudo(comentarioRequest.dsConteudo());
        comentarioModel.setHrCriacao(LocalTime.now());
        comentarioModel.setDtCriacao(LocalDate.now());

        comentarioRepository.save(comentarioModel);

        return new ComentarioResponseDTO(comentarioModel);
    }

    @Transactional
    public ComentarioResponseDTO criarComentarioComAnexo(ComentarioAnexoRequestDTO comentarioRequest)
            throws IOException {

        var usuario = usuarioRepository.findById(comentarioRequest.cdUsuario())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(comentarioRequest.cdUsuario()));

        var chamado = chamadoRepository.findById(comentarioRequest.cdChamado())
                .orElseThrow(() -> new ChamadoNaoEncontradoException(comentarioRequest.cdChamado()));

        ComentarioModel comentarioModel = new ComentarioModel();
        comentarioModel.setUsuario(usuario);
        comentarioModel.setChamado(chamado);
        comentarioModel.setDsConteudo(comentarioRequest.dsConteudo());
        comentarioModel.setHrCriacao(LocalTime.now());
        comentarioModel.setDtCriacao(LocalDate.now());

        MultipartFile arquivo = comentarioRequest.anexo();
        if (arquivo != null && !arquivo.isEmpty()) {
            AnexoModel anexoModel = new AnexoModel();
            anexoModel.setChamado(chamado);
            anexoModel.setUsuario(usuario);
            anexoModel.setDtUpload(LocalDate.now());
            anexoModel.setHrUpload(LocalTime.now());
            anexoModel.setNmArquivo(arquivo.getOriginalFilename());
            anexoModel.setDsTipoArquivo(arquivo.getContentType());
            anexoModel.setArquivo(arquivo.getBytes());

            anexoRepository.save(anexoModel);
            comentarioModel.setAnexo(anexoModel);
        }

        comentarioRepository.save(comentarioModel);

        return new ComentarioResponseDTO(comentarioModel);
    }

    @Transactional
    public List<ComentarioResponseDTO> listarComentarios(Long cdChamado) {
        List<ComentarioResponseDTO> comentarios = new ArrayList<>();

        List<ComentarioModel> comentarioModel = comentarioRepository.findAllByChamado_CdChamado(cdChamado);
        for(ComentarioModel comentario :  comentarioModel){
            comentarios.add(new ComentarioResponseDTO(comentario));
        }
        return comentarios;
    }
}
