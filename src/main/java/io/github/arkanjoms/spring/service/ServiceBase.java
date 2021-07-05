package io.github.arkanjoms.spring.service;

import io.github.arkanjoms.spring.dto.RequestBase;
import io.github.arkanjoms.spring.dto.ResponseBase;
import io.github.arkanjoms.spring.entity.EntityBase;
import io.github.arkanjoms.spring.exception.DataException;
import io.github.arkanjoms.spring.exception.DataNotFoundException;
import io.github.arkanjoms.spring.exception.IdentifierNotEqualsException;
import io.github.arkanjoms.spring.repository.RepositoryBase;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * Generic service class
 *
 * @param <E> Entity
 * @param <I> ID
 * @param <R> Repository
 * @param <Q> Request DTO
 * @param <P> Response DTO
 */
@Slf4j
public abstract class ServiceBase<
        E extends EntityBase<I>,
        I extends Serializable,
        R extends RepositoryBase<E, I, P>,
        Q extends RequestBase<I>,
        P extends ResponseBase<I>> {

    private final Class<E> entityType;
    private final Class<P> responseType;

    private final R repository;
    private final ModelMapper modelMapper;

    protected ServiceBase(R repository) {
        this.repository = repository;
        this.modelMapper = new ModelMapper();
        this.entityType = (Class<E>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.responseType = (Class<P>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[4];
    }

    /**
     * Find all with paging and sorting
     *
     * @param page paging and sorting object
     * @return paginated list of response of entity
     */
    public Page<P> findAll(Pageable page) {
        return repository.findAll(page);
    }

    /**
     * Save new entity
     *
     * @param data request data of entity
     * @return response of entity
     */
    public P create(Q data) {
        var entity = modelMapper.map(data, getEntityClass());
        var saved = repository.save((E) entity);
        return (P) modelMapper.map(saved, getResponseClass());
    }

    /**
     * Edit and entity
     *
     * @param id   entity ID
     * @param data request data of entity
     * @return response of entity
     */
    public P edit(I id, Q data) {
        if (!id.equals(data.getId())) {
            throw new IdentifierNotEqualsException();
        }
        var entity = modelMapper.map(data, getEntityClass());
        var saved = repository.save((E) entity);
        return (P) modelMapper.map(saved, getResponseClass());
    }

    /**
     * Find entity by ID
     *
     * @param id identifier for filter
     * @return response of entity
     */
    public P findById(I id) {
        var author = repository
                .findById(id)
                .orElseThrow(DataNotFoundException::new);
        return (P) modelMapper.map(author, getResponseClass());
    }

    /**
     * delete and entity by ID
     *
     * @param id identifier for delete
     */
    public void deleteById(I id) {
        if (!repository.existsById(id)) {
            throw new DataNotFoundException();
        }
        repository.deleteById(id);
    }

    private Class<?> getEntityClass() {
        try {
            return Class.forName(entityType.getTypeName());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
            throw new DataException("class not found", e);
        }
    }

    private Class<?> getResponseClass() {
        try {
            return Class.forName(responseType.getTypeName());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage());
            throw new DataException("class not found", e);
        }
    }
}
