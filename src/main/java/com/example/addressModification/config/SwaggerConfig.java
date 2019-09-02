package com.example.addressModification.config;


import com.example.addressModification.utils.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * tester les Apis Rest sur
 * https://localhost:8443/swagger-ui.html#
 * (aprés avoir  demarré le serveur embarqué de spring-web , par spring-boot:run )
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.addressModification.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo())
                        .tags(new Tag(Constants.TAG_SUBSCRIBER, "API permettant la manipulation des Abonnées."),
                                new Tag(Constants.TAG_CONTRACT,"API de gestion des contrats")
);
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                " SUBSCRIBER UPDATE ADDRESS API",
                "API project to expose CRUD on Abonnées/contrats to all apps"
                ,
                "1.0",
        null,
                new Contact(null, null, null),
                null,
                null);
        return apiInfo;
    }
}
