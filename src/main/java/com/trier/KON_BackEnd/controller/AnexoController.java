package com.trier.KON_BackEnd.controller;

import com.trier.KON_BackEnd.dto.request.AnexoRequestDTO;
import com.trier.KON_BackEnd.dto.response.AnexoResponseDTO;
import com.trier.KON_BackEnd.services.AnexoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/anexo")
public class AnexoController {

    @Autowired
    private AnexoService anexoService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AnexoResponseDTO> uploadArquivo(@RequestParam("file") MultipartFile file,
                                                          @ModelAttribute("anexo") AnexoRequestDTO anexoRequest) throws Exception {

        return ResponseEntity.status(HttpStatus.CREATED).body(anexoService.uploadArquivo(anexoRequest, file));
    }

    @GetMapping("/baixar/{cdAnexo}")
    public ResponseEntity<AnexoResponseDTO> downloadArquivo(@PathVariable("cdAnexo") Long cdAnexo) {

        return ResponseEntity.status(HttpStatus.OK).body(anexoService.downloadArquivo(cdAnexo));
    }

    @GetMapping("/anexos")
    public ResponseEntity<List<AnexoResponseDTO>> listarAnexos() {

        return ResponseEntity.status(HttpStatus.OK).body(anexoService.listarAnexos());
    }
}
