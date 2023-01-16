package com.applicationcore.core.service;

import java.io.Serializable;

import com.applicationcore.core.domain.EntityImp;

public interface ServiceImp<T extends EntityImp<S>, S extends Serializable> {

    default void beforeSave(T entity) {
    }

    default void afterSave(T entity) {
    }

    T save(T entity);

}
