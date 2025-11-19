package com.trier.KON_BackEnd.services;

import com.trier.KON_BackEnd.dto.request.PlanoRequestDTO;
import com.trier.KON_BackEnd.dto.response.PlanoResponseDTO;
import com.trier.KON_BackEnd.dto.response.UsuarioResponseDTO;
import com.trier.KON_BackEnd.model.PlanoModel;
import com.trier.KON_BackEnd.model.UsuarioModel;
import com.trier.KON_BackEnd.repository.PlanoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlanoService {

    private final PlanoRepository planoRepository;

    @Transactional
    public PlanoResponseDTO cadastro(PlanoRequestDTO dto) {
        var plano = new PlanoModel();
        plano.setNmPlano(dto.nmPlano());
        plano.setVlPlano(dto.vlPlano());
        plano.setLimiteUsuarios(dto.limiteUsuarios());
        plano.setHrRespostaPlano(dto.hrRespostaPlano());
        plano.setHrResolucaoPlano(dto.hrResolucaoPlano());


         planoRepository.save(plano);

         return new PlanoResponseDTO(
                 plano.getCdPlano(),
                 plano.getNmPlano(),
                 plano.getVlPlano(),
                 plano.getLimiteUsuarios(),
                 plano.getHrRespostaPlano(),
                 plano.getHrResolucaoPlano()
         );

    }

    public List<PlanoResponseDTO> listarTodos() {
        List<PlanoModel> plano = planoRepository.findAll();
        return plano.stream().map(this::converterParaResponse).collect(Collectors.toList());
    }

    private PlanoResponseDTO converterParaResponse(PlanoModel plano) {
        return new PlanoResponseDTO(
                plano.getCdPlano(),
                plano.getNmPlano(),
                plano.getVlPlano(),
                plano.getLimiteUsuarios(),
                plano.getHrRespostaPlano(),
                plano.getHrResolucaoPlano()

        );

    }
}
