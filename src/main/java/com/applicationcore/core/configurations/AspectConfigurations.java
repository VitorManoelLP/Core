package com.applicationcore.core.configurations;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Configuration
@EnableAspectJAutoProxy
public class AspectConfigurations {

    @Bean
    public RepositoryAspect repositoryAspect(EntityManagerFactory emf) {
        return new RepositoryAspect(emf);
    }

}
