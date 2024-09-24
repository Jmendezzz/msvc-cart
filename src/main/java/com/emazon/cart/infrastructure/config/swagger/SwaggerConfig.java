package com.emazon.cart.infrastructure.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

import static com.emazon.cart.infrastructure.utils.constants.SwaggerConstant.*;

@OpenAPIDefinition(
        info = @Info(
                title = API_TITLE,
                description = API_DESCRIPTION,
                version = API_VERSION,
                contact = @Contact(
                        name = CONTACT_NAME,
                        email = CONTACT_EMAIL)
        ),
        servers = {
                @Server(url = SERVER_URL, description = SERVER_DESCRIPTION),
        },
        security = @SecurityRequirement(name = SECURITY_NAME)
)
@SecurityScheme(
        name= SECURITY_NAME,
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = SCHEME,
        bearerFormat = BEARER_FORMAT
)
public class SwaggerConfig {
}
