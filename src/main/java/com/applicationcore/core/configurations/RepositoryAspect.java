package com.applicationcore.core.configurations;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.Session;
import org.hibernate.annotations.FilterDef;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;

import lombok.SneakyThrows;

@Aspect
public class RepositoryAspect {

    private final EntityManagerFactory emf;

    public RepositoryAspect(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Pointcut("within(@com.applicationcore.core.repository.filter_annotation.FilterRepository *)")
    public void repositoryPointcut() { /* Only pointcut annotation */ }

    @SneakyThrows
    @Around(value = "repositoryPointcut()")
    public Object enableFilterDef(ProceedingJoinPoint joinPoint) {

        Optional<Class<?>> aClass = getClassWithFilterAnnotation(joinPoint.getThis());

        aClass.flatMap(clazz -> Arrays.stream(clazz.getGenericInterfaces())
                .findFirst()).ifPresent(genericType -> {

            ParameterizedType parameterizedType = (ParameterizedType) genericType;

            Stream.of(parameterizedType.getActualTypeArguments()).findFirst()
                    .ifPresent(domain -> {

                        Class<?> domainImpRepository = (Class<?>) domain;

                        FilterDef filterDef = AnnotationUtils.getAnnotation(domainImpRepository,
                                FilterDef.class);

                        if (Objects.nonNull(filterDef)) {
                            Session session = getSession();
                            session.enableFilter(filterDef.name());
                        }

                    });

        });

        return joinPoint.proceed();
    }

    private Optional<Class<?>> getClassWithFilterAnnotation(Object instance) {
        return Optional.ofNullable(instance.getClass())
                .map(clazz -> Arrays.stream(clazz.getInterfaces())
                        .findFirst()
                        .orElseThrow());
    }

    public Session getSession() {

        EntityManager entityManager = Objects.requireNonNull(
                EntityManagerFactoryUtils.getTransactionalEntityManager(emf));

        return entityManager.unwrap(Session.class);
    }

}
