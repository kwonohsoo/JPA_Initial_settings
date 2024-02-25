package com.demo.domain.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "임산물 데모",
                description = "임산물 프로젝트 초기 세팅",
                version = "1.0.0"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi group1() {
        String[] paths = {"/board/create"};

        return GroupedOpenApi.builder()
                .group("등록")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi group2() {
        String[] paths = {"/board/view/**"};

        return GroupedOpenApi.builder()
                .group("조회")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi group3() {
        String[] paths = {"/board/update/**"};

        return GroupedOpenApi.builder()
                .group("수정")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi group4() {
        String[] paths = {"/board/delete/**"};

        return GroupedOpenApi.builder()
                .group("삭제")
                .pathsToMatch(paths)
                .build();
    }
}