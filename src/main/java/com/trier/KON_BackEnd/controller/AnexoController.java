package com.trier.KON_BackEnd.controller;

import com.trier.KON_BackEnd.dto.request.AnexoRequestDTO;
import com.trier.KON_BackEnd.dto.response.AnexoResponseDTO;
import com.trier.KON_BackEnd.model.AnexoModel;
import com.trier.KON_BackEnd.services.AnexoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Anexo", description = "Gerenciamento de arquivos")
public class AnexoController {

    @Autowired
    private AnexoService anexoService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Enviar arquivo",
            description = "Realiza o upload de um arquivo para o sistema. O arquivo é salvo no banco de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Arquivo enviado com sucesso",
                    content = @Content(schema = @Schema(implementation = AnexoResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados", content = @Content),
            @ApiResponse(responseCode = "404", description = "Usuário ou chamado não encontrado", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    public ResponseEntity<AnexoResponseDTO> uploadArquivo(@ModelAttribute
                                                          @Valid AnexoRequestDTO anexoRequest
    ) throws IOException {

        return ResponseEntity.status(HttpStatus.CREATED).body(anexoService.uploadArquivo(anexoRequest));
    }


    @GetMapping("/baixar/{cdAnexo}")
    @Operation(
            summary = "Baixar arquivo",
            description = "Realiza o download de um arquivo armazenado no banco de dados."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arquivo baixado com sucesso",
                    content = @Content(schema = @Schema(implementation = AnexoResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Arquivo não encontrado no banco de dados", content =  @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    public ResponseEntity<byte[]> downloadArquivo(@Parameter(description = "ID do anexo", example = "12")
                                                  @PathVariable("cdAnexo") Long cdAnexo) {

        AnexoModel anexo = anexoService.downloadArquivo(cdAnexo);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + anexo.getNmArquivo() + "\"")
                .contentType(MediaType.parseMediaType(anexo.getDsTipoArquivo()))
                .body(anexo.getArquivo());
    }


    @GetMapping("/anexos")
    @Operation(summary = "Listar arquivos", description = "Lista todos os arquivos do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listando arquivos",
            content = @Content(schema = @Schema(implementation = AnexoResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Erro no servidor", content = @Content)
    })
    public ResponseEntity<List<AnexoResponseDTO>> listarAnexos() {

        return ResponseEntity.status(HttpStatus.OK).body(anexoService.listarAnexos());
    }
}
