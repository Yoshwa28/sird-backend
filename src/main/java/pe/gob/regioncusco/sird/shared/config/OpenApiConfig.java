package pe.gob.regioncusco.sird.shared.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI sirdOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SIRD API")
                        .version("v1.0")
                        .description("API Backend del Sistema Institucional de Repositorio Digital - SIRD")
                        .contact(new Contact()
                                .name("Gobierno Regional del Cusco")
                                .email("soporte.sird@regioncusco.gob.pe"))
                        .license(new License()
                                .name("Uso institucional")));
    }
}
