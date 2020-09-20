package com.myprojects.carpark.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


//Klasa konfigurująca Swaggera, czyli interfejsu slużącego do opisywania aplikacji RESTowych wyrażonych za pomocą JSON.
//Swagger jest używany razem z zestawem narzędzi oprogramowania typu open source do projektowania, tworzenia, dokumentowania i korzystania z usług RESTowych aplikacji.
//@EnableScheduling - Włącza schedulere, dzięki któremu działa swagger
//@EnableSwagger2 - Pozawala na użycie Swaggera
//@Configuration - oznacza klasę konfigurującą beany, na podstawie tej adnotacji Spring tworzy beany
@EnableScheduling
@EnableSwagger2
@Configuration
public class SwaggerConfiguration implements WebMvcConfigurer {

    //Metoda konfigurująca swaggera i w jakim wolderze ma szukać RESTowych endpointów
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.myprojects.carpark"))
                .paths(PathSelectors.any())
                .build();
    }
}
