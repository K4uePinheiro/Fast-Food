
package local.kaue.FastFuriousFood;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author sesideva
 */
@Configuration
@OpenAPIDefinition(info = @Info(title = "REDSapp", version = "v1"))
@SecurityScheme(
 name = "ApiKeyAuth",
 type = SecuritySchemeType.APIKEY,
 in = SecuritySchemeIn.HEADER,
 paramName = "X-API-KEY"
)
public class OpenApiConfig {
    
}
