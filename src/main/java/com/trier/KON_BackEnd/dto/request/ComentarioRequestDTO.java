package com.trier.KON_BackEnd.dto.request;

import com.trier.KON_BackEnd.model.ChamadoModel;
import com.trier.KON_BackEnd.model.Usuario;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ComentarioRequestDTO(

        @NotNull(message = "O código do chamado é obrigatório.")
        ChamadoModel cdChamado,

        @NotNull(message = "O código de Usuário é obrigatório.")
        Usuario cdUsuario,

        @Size(min = 2, max = 1000, message = "O conteúdo deve ter entre 2 e 1000 caracteres.")
        String dsConteudo

) {
}
