package com.trier.KON_BackEnd.services;

import com.trier.KON_BackEnd.dto.request.CategoriaRequestDTO;
import com.trier.KON_BackEnd.dto.request.CategoriaUpdateRequestDTO;
import com.trier.KON_BackEnd.dto.response.CategoriaResponseDTO;
import com.trier.KON_BackEnd.model.CategoriaModel;
import com.trier.KON_BackEnd.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public CategoriaResponseDTO cadastrarCategoria(CategoriaRequestDTO categoriaRequest) {

        CategoriaModel categoria = new CategoriaModel();

        categoria.setNmCategoria(categoriaRequest.nmCategoria());
        categoria.setFlAtivo(categoriaRequest.flAtivo());

        categoriaRepository.save(categoria);

        return new CategoriaResponseDTO(

                categoria.getCdCategoria(),
                categoria.getNmCategoria(),
                categoria.getFlAtivo()

        );

    }

    @Transactional
    public CategoriaResponseDTO atualizarCategoria(Long cdCategoria, CategoriaUpdateRequestDTO categoriaRequest) {

        var categoria = categoriaRepository.findById(cdCategoria)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));

        categoria.setNmCategoria(categoriaRequest.nmCategoria());

        if (categoriaRequest.flAtivo() != null) {
            categoria.setFlAtivo(categoriaRequest.flAtivo());
        }

        categoriaRepository.save(categoria);

        return new CategoriaResponseDTO(
                categoria.getCdCategoria(),
                categoria.getNmCategoria(),
                categoria.getFlAtivo()
        );
    }

    @Transactional
    public CategoriaResponseDTO desativarCategoria(Long cdCategoria) {

        var categoria = categoriaRepository.findById(cdCategoria)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));

        categoria.setFlAtivo(false);

        categoriaRepository.save(categoria);

        return new CategoriaResponseDTO(

                categoria.getCdCategoria(),
                categoria.getNmCategoria(),
                categoria.getFlAtivo()

        );

    }

    @Transactional
    public CategoriaResponseDTO reativarCategoria(Long cdCategoria) {

        var categoria = categoriaRepository.findById(cdCategoria)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada!"));

        categoria.setFlAtivo(true);

        categoriaRepository.save(categoria);

        return new CategoriaResponseDTO(

                categoria.getCdCategoria(),
                categoria.getNmCategoria(),
                categoria.getFlAtivo()

        );

    }

    @Transactional
    public CategoriaResponseDTO listarCategoria(Long cdCategoria) {

        var categoria = categoriaRepository.findById(cdCategoria)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada"));

        return convertToResponseDTO(categoria);

    }

    @Transactional
    public List<CategoriaResponseDTO> listarCategoriasAtivas() {

        List<CategoriaModel> categoria = categoriaRepository.findAllByFlAtivo();

        return categoria.stream().map(this:: convertToResponseDTO).toList();

    }

    @Transactional
    public List<CategoriaResponseDTO> listarTodasCategorias() {

        List<CategoriaModel> categoria = categoriaRepository.findAll();

        return categoria.stream().map(this:: convertToResponseDTO).toList();

    }

    private CategoriaResponseDTO convertToResponseDTO(CategoriaModel categoria) {

        return new CategoriaResponseDTO(

                categoria.getCdCategoria(),
                categoria.getNmCategoria(),
                categoria.getFlAtivo()

        );

    }

}
