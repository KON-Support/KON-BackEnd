package com.trier.KON_BackEnd.controller;

import com.trier.KON_BackEnd.dto.request.AnexoRequestDTO;
import com.trier.KON_BackEnd.dto.response.AnexoResponseDTO;
import com.trier.KON_BackEnd.services.AnexoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/anexo")
public class AnexoController {

    @Autowired
    private AnexoService anexoService;

    @PostMapping("/upload")
    public AnexoResponseDTO uploadArquivo(@RequestParam("file") MultipartFile file,
                                        @RequestBody AnexoRequestDTO anexoRequest) throws Exception {
        AnexoResponseDTO response = anexoService.uploadArquivo(anexoRequest, file);

        return response;
    }

    @GetMapping("/baixar/{cdAnexo}")
    public AnexoResponseDTO downloadArquivo(@PathVariable("cdAnexo") Long cdAnexo) {
        AnexoResponseDTO response = anexoService.downloadArquivo(cdAnexo);

        return response;
    }

    @GetMapping("/anexos")
    public List<AnexoResponseDTO> listarAnexos() {
        List<AnexoResponseDTO> response = anexoService.listarAnexos();

        return response;
    }
}
