package com.upiiz.discounts.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Discounts API",
                description = "API para gestionar descuentos con CRUD completo y protección de roles.",
                version = "1.0.0",
                contact = @Contact(
                        name = "Raul Cardoso Acevedo",
                        email = "rcardosoa2100@alumno.ipn.mx",
                        url = "http://localhost:8080/api/discounts"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/licenses/MIT"
                ),
                termsOfService = "Solo usuarios autorizados pueden acceder."
        ),
        servers = {
                @Server(
                        description = "Servidor de desarrollo",
                        url = "http://localhost:8080"
                ),
                @Server(
                        description = "Servidor de producción",
                        url = "Render"
                )
        },
        tags = {
                @Tag(
                        name = "descuentos",
                        description = "Operaciones relacionadas con la gestión de descuentos."
                )
        }
)
public class OpenApiConfiguration {
}
