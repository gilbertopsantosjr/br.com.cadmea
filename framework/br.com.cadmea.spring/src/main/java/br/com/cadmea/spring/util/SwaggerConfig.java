package br.com.cadmea.spring.util;

import com.google.common.base.Predicate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "system_test")
public class SwaggerConfig {

    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
                .apiInfo(apiInfo()).select().paths(postPaths()).build();
    }

    private Predicate<String> postPaths() {
        return or(regex("/api/public.*"), regex("/api/private.*"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Receita")
                .description("Receitas para quando estou com fome")
                .termsOfServiceUrl("http://gilbertosantos.com")
                .contact("gilbertopsantosjr@gmail.com").license("Gilberto Santos License")
                .licenseUrl("gilbertopsantosjr@gmail.com").version("1.0").build();
    }
}
