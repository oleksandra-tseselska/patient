package com.bloodmatch.bloodstorage.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class Config {

    @Bean
    public Docket swaggerConfiguration() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/error").negate())
                .build()
                .apiInfo(apiInfo());
        docket.useDefaultResponseMessages(false);
        return appendTags(docket);

    }

    private Docket appendTags(Docket docket) {
        return docket.tags(
                new Tag(DescriptionVariables.BLOOD_GROUP,
                        "Used to get blood group"),
                new Tag(DescriptionVariables.PATIENT,
                        "Used to get, create, update patient"));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Blood stack management systems API")
                .description("Blood stack management systems API. Second chance to live")
                .version("1.0")
                .build();
    }
}
