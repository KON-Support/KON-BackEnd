package com.trier.KON_BackEnd.services;

import com.trier.KON_BackEnd.dto.request.AnexoRequestDTO;
import com.trier.KON_BackEnd.dto.response.AnexoResponseDTO;
import com.trier.KON_BackEnd.exception.AnexoNaoEncontradoException;
import com.trier.KON_BackEnd.exception.ChamadoNaoEncontradoException;
import com.trier.KON_BackEnd.exception.UsuarioNaoEncontradoException;
import com.trier.KON_BackEnd.model.AnexoModel;
import com.trier.KON_BackEnd.repository.AnexoRepository;
import com.trier.KON_BackEnd.repository.ChamadoRepository;
import com.trier.KON_BackEnd.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnexoService {

    @Autowired
    private AnexoRepository anexoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ChamadoRepository chamadoRepository;

    @Transactional
    public AnexoResponseDTO uploadArquivo(AnexoRequestDTO anexoRequest, MultipartFile file) throws IOException {
        var usuario = usuarioRepository.findById(anexoRequest.cdUsuario())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(anexoRequest.cdUsuario()));

        var chamado = chamadoRepository.findById(anexoRequest.cdChamado())
                .orElseThrow(() -> new ChamadoNaoEncontradoException(anexoRequest.cdChamado()));

        AnexoModel anexo = new AnexoModel();

        anexo.setCdChamado(chamado);
        anexo.setCdUsuario(usuario);
        anexo.setNmArquivo(file.getName());
        anexo.setDsTipoArquivo(file.getContentType());
        anexo.setArquivo(file.getBytes());

        anexoRepository.save(anexo);

        return new AnexoResponseDTO(
                anexo.getCdAnexo(),
                anexo.getCdChamado().getCdChamado(),
                anexo.getCdUsuario().getCdUsuario(),
                anexo.getNmArquivo(),
                anexo.getDsTipoArquivo()
        );
    }

    @Transactional
    public AnexoResponseDTO downloadArquivo(Long cdAnexo) {
        var anexo = anexoRepository.findById(cdAnexo)
                .orElseThrow(() -> new AnexoNaoEncontradoException(cdAnexo));

        return new AnexoResponseDTO(
                anexo.getCdAnexo(),
                anexo.getCdChamado().getCdChamado(),
                anexo.getCdUsuario().getCdUsuario(),
                anexo.getNmArquivo(),
                anexo.getDsTipoArquivo()
        );
    }
    @Transactional
    public List<AnexoResponseDTO> listarAnexos() {
        List<AnexoResponseDTO> anexos = new ArrayList<>();

        List<AnexoModel> anexoModel = anexoRepository.findAll();
        for (AnexoModel anexo : anexoModel) {
            anexos.add(new AnexoResponseDTO(anexo));
        }
        return anexos;
    }
}
