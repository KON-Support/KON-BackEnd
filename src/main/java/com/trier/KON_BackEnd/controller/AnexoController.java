package com.trier.KON_BackEnd.controller;

import com.trier.KON_BackEnd.dto.request.AnexoRequestDTO;
import com.trier.KON_BackEnd.dto.response.AnexoResponseDTO;
import com.trier.KON_BackEnd.model.AnexoModel;
import com.trier.KON_BackEnd.services.AnexoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/anexo")
public class AnexoController {

    @Autowired
    private AnexoService anexoService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AnexoResponseDTO> uploadArquivo(@ModelAttribute
                                                          @Valid AnexoRequestDTO anexoRequest) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED).body(anexoService.uploadArquivo(anexoRequest));
    }

    @GetMapping("/baixar/{cdAnexo}")
    public ResponseEntity<byte[]> downloadArquivo(@PathVariable("cdAnexo") Long cdAnexo) {
        AnexoModel anexo = anexoService.downloadArquivo(cdAnexo);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + anexo.getNmArquivo() + "\"")
                .contentType(MediaType.parseMediaType(anexo.getDsTipoArquivo()))
                .body(anexo.getArquivo());
    }

    @GetMapping("/anexos")
    public ResponseEntity<List<AnexoResponseDTO>> listarAnexos() {

        return ResponseEntity.status(HttpStatus.OK).body(anexoService.listarAnexos());
    }
}
