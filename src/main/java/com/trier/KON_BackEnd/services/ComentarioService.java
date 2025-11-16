package com.trier.KON_BackEnd.services;

import com.trier.KON_BackEnd.dto.request.ComentarioRequestDTO;
import com.trier.KON_BackEnd.dto.response.ComentarioResponseDTO;
import com.trier.KON_BackEnd.exception.ChamadoNaoEncontradoException;
import com.trier.KON_BackEnd.exception.UsuarioNaoEncontradoException;
import com.trier.KON_BackEnd.model.ComentarioModel;
import com.trier.KON_BackEnd.repository.ChamadoRepository;
import com.trier.KON_BackEnd.repository.ComentarioRepository;
import com.trier.KON_BackEnd.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<ComentarioResponseDTO> listarComentarios() {
        List<ComentarioResponseDTO> comentarios = new ArrayList<>();

        List<ComentarioModel> comentarioModel = comentarioRepository.findAll();
        for(ComentarioModel comentario :  comentarioModel){
            comentarios.add(new ComentarioResponseDTO(comentario));
        }
        return comentarios;
    }
}
