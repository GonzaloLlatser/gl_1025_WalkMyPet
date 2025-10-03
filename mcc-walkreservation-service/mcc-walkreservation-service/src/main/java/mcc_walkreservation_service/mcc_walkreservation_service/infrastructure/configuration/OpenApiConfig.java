package mcc_walkreservation_service.mcc_walkreservation_service.infrastructure.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
      .info(new Info()
        .title("Walk reservation Service API")
        .version("1.0")
        .description("API for managing walk reservation in the WalkMyPet application"));
  }
}