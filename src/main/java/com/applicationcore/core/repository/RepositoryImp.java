package com.applicationcore.core.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.applicationcore.core.domain.EntityImp;

@NoRepositoryBean
public interface RepositoryImp<T extends EntityImp<S>, S extends Serializable> extends JpaRepository<T, S> {

    default T findOne(S id) {
        return findById(id).orElse(null);
    }

}
