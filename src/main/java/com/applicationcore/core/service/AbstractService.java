package com.applicationcore.core.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.fintech.demo.core.domain.EntityImp;
import com.fintech.demo.core.repository.RepositoryImp;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractService<T extends EntityImp<S>, S extends Serializable> implements ServiceImp<T, S> {

    @Autowired
    @Getter
    @Setter
    private RepositoryImp<T, S> repository;

    @Override
    public T save(T entity) {
        beforeSave(entity);
        repository.save(entity);
        afterSave(entity);
        return entity;
    }

    @SuppressWarnings("unchecked")
    public <A extends RepositoryImp<?, ?>> A getRepository(Class<A> repository) {
        return (A) getRepository();
    }
}
