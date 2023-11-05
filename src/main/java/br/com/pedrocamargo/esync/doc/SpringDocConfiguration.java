package br.com.pedrocamargo.esync.doc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {

    @Bean
    public OpenAPI openAPIDefinition() {
        return new OpenAPI()
            .info(
                new Info()
                    .title("ESync - Controle de Estoque")
                    .version("v0.0.1")
                    .description("Backend do aplicativo ESync")
                    .contact(
                            new Contact()
                                    .name("Pedro Camargo")
                                    .email("pedroch23@yahoo.com.br")
                    )
            )
            .components(
                new Components()
                    .addSecuritySchemes(
                            "bearer-key",
                            new SecurityScheme()
                                    .type(SecurityScheme.Type.HTTP)
                                    .scheme("bearer")
                                    .bearerFormat("JWT")
                    )
            );
    }

}
