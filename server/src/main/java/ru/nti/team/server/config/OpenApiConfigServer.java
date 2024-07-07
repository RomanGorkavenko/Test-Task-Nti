package ru.nti.team.server.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfigServer {

    /**
     * Конфигурация OpenApi.
     * @return {@link OpenAPI} конфигурация OpenApi.
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Test task API Server")
                        .description("Spring boot application")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Roman Gorkavenko")
                                .email("roman@gorkavenko.ru"))
                );
    }

}
