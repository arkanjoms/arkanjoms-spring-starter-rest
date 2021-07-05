package io.github.arkanjoms.spring.exampleapp.repository;

import io.github.arkanjoms.spring.exampleapp.dto.ExampleResponse;
import io.github.arkanjoms.spring.exampleapp.entity.Example;
import io.github.arkanjoms.spring.repository.RepositoryBase;

public interface ExampleRepository extends RepositoryBase<Example, Long, ExampleResponse> {
}
