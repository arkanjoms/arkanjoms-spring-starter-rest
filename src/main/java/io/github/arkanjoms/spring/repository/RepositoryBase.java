package io.github.arkanjoms.spring.repository;

import io.github.arkanjoms.spring.dto.ResponseBase;
import io.github.arkanjoms.spring.entity.EntityBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Base repository interface
 *
 * @param <E> Entity
 * @param <I> ID
 * @param <P> Response DTO
 */
@NoRepositoryBean
public interface RepositoryBase<
        E extends EntityBase<I>,
        I extends Serializable,
        P extends ResponseBase<I>> extends CrudRepository<E, I> {
    Page<P> findAll(Pageable page);
}
