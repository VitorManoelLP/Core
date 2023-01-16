package com.applicationcore.core.resource;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.applicationcore.core.domain.EntityImp;
import com.applicationcore.core.repository.RepositoryImp;
import com.applicationcore.core.service.ServiceImp;

public class AbstractResource<T extends EntityImp<S>, S extends Serializable> {

    @Autowired
    private RepositoryImp<T, S> repository;

    @Autowired
    private ServiceImp<T, S> service;

    @GetMapping("/unpaged")
    public ResponseEntity<List<T>> findAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping
    public ResponseEntity<Page<T>> findAll(@PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(repository.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@PathVariable S id) {
        return ResponseEntity.ok(repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Entity não encontrada")));
    }

    @PostMapping("/new")
    public ResponseEntity<T> save(@RequestBody T entity) {
        service.beforeSave(entity);
        T saved = repository.save(entity);
        service.afterSave(entity);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable S id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    public RepositoryImp<T, S> getRepository() {
        return repository;
    }

    public ServiceImp<T, S> getService() {
        return service;
    }

    @SuppressWarnings("unchecked")
    public <A extends ServiceImp<T, S>> A getService(Class<A> clazz) {
        return (A) getService();
    }

    public void setRepository(RepositoryImp<T, S> repository) {
        this.repository = repository;
    }

    public void setService(ServiceImp<T, S> service) {
        this.service = service;
    }
}
