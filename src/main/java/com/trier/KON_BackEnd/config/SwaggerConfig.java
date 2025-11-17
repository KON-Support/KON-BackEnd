package com.trier.KON_BackEnd.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Testes - KON Support")
                        .version("v1")
                        .description("Esta API foi desenvolvida para demonstrar, validar e documentar os recursos e integrações do sistema KON Support. "
                                + "Utilizando o Swagger/OpenAPI, é possível explorar os endpoints disponíveis, visualizar modelos de dados, "
                                + "realizar chamadas de teste e facilitar o processo de desenvolvimento, manutenção e integração entre serviços."));
    }
}