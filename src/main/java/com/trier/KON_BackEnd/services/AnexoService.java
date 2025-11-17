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
import java.time.LocalDate;
import java.time.LocalTime;
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
    public AnexoResponseDTO uploadArquivo(AnexoRequestDTO anexoRequest) throws IOException {
        var usuario = usuarioRepository.findById(anexoRequest.cdUsuario())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(anexoRequest.cdUsuario()));

        var chamado = chamadoRepository.findById(anexoRequest.cdChamado())
                .orElseThrow(() -> new ChamadoNaoEncontradoException(anexoRequest.cdChamado()));

        AnexoModel anexoModel = new AnexoModel();
        anexoModel.setChamado(chamado);
        anexoModel.setUsuario(usuario);
        anexoModel.setDtUpload(LocalDate.now());
        anexoModel.setHrUpload(LocalTime.now());

        MultipartFile arquivo = anexoRequest.anexo();
        anexoModel.setNmArquivo(arquivo.getOriginalFilename());
        anexoModel.setDsTipoArquivo(arquivo.getContentType());
        anexoModel.setArquivo(arquivo.getBytes());

        anexoRepository.save(anexoModel);

        return new AnexoResponseDTO(
                anexoModel.getCdAnexo(),
                anexoModel.getChamado().getCdChamado(),
                anexoModel.getUsuario().getCdUsuario(),
                anexoModel.getNmArquivo(),
                anexoModel.getDsTipoArquivo(),
                anexoModel.getDtUpload(),
                anexoModel.getHrUpload()
        );
    }

    @Transactional
    public AnexoModel downloadArquivo(Long cdAnexo) {

        return anexoRepository.findById(cdAnexo)
                .orElseThrow(() -> new AnexoNaoEncontradoException(cdAnexo));
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
