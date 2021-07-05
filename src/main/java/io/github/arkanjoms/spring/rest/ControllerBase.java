package io.github.arkanjoms.spring.rest;

import io.github.arkanjoms.spring.dto.RequestBase;
import io.github.arkanjoms.spring.dto.ResponseBase;
import io.github.arkanjoms.spring.service.ServiceBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.net.URI;

/**
 * Generic Controller class
 *
 * @param <Q> Request DTO
 * @param <P> Response DTO
 * @param <I> ID
 */
public abstract class ControllerBase<Q extends RequestBase<I>, P extends ResponseBase<I>, I extends Serializable> {

    private final ServiceBase service;

    protected ControllerBase(ServiceBase service) {
        this.service = service;
    }

    /**
     * GET Find all pageable
     *
     * @param page pageable
     * @return page of entity
     */
    @GetMapping
    public ResponseEntity<Page<P>> findAll(Pageable page) {
        return ResponseEntity.ok(service.findAll(page));
    }

    /**
     * POST create an entity
     *
     * @param data request data
     * @return response of entity
     */
    @PostMapping
    public ResponseEntity<P> create(@RequestBody Q data) {
        var created = service.create(data);
        return ResponseEntity
                .created(URI.create("/api/authors/" + created.getId()))
                .body((P) created);
    }

    /**
     * PUT edit an entity
     *
     * @param id   path variable ID
     * @param data request data
     * @return response of entity
     */
    @PutMapping("/{id}")
    public ResponseEntity<P> edit(
            @PathVariable("id") I id,
            @RequestBody Q data) {
        return ResponseEntity.ok((P) service.edit(id, data));
    }

    /**
     * GET find entity by ID
     *
     * @param id path variable ID
     * @return response of entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<P> findById(@PathVariable("id") I id) {
        return ResponseEntity.ok((P) service.findById(id));
    }

    /**
     * DELETE remove an entity by ID
     *
     * @param id path variable ID
     * @return response no content if delete was successfully complete
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") I id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
