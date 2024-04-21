package com.kathesama.app.service.infrastructure.configuration;

import com.kathesama.app.common.model.Role;
import com.kathesama.app.service.infrastructure.adapter.output.persistence.entity.UserEntity;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RepositoryConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        RepositoryRestConfigurer.super.configureRepositoryRestConfiguration(config, cors);

        config.exposeIdsFor(UserEntity.class, Role.class);
    }
}
