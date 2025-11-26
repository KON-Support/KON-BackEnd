package com.trier.KON_BackEnd.dto.request;

import jakarta.validation.constraints.*;
import java.util.List;

public record UsuarioRequestDTO (
        Long cdUsuario,

        @NotBlank(message = "O nome do usuário é obrigatório")
        @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres")
        String nmUsuario,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$",
                message = "A senha deve conter pelo menos uma letra maiúscula, uma minúscula, um número e um caractere especial")
        String dsSenha,

        @NotNull(message = "A quantidade de funcionários deve ser informada.")
        Integer nuFuncionario,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "E-mail inválido")
        @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres")
        String dsEmail,

        @NotNull(message = "O status ativo é obrigatório")
        boolean flAtivo,

        List<String>roles
) {


}
