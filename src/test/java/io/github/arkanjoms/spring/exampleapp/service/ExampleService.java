package io.github.arkanjoms.spring.exampleapp.service;

import io.github.arkanjoms.spring.exampleapp.dto.ExampleRequest;
import io.github.arkanjoms.spring.exampleapp.dto.ExampleResponse;
import io.github.arkanjoms.spring.exampleapp.entity.Example;
import io.github.arkanjoms.spring.exampleapp.repository.ExampleRepository;
import io.github.arkanjoms.spring.service.ServiceBase;
import org.springframework.stereotype.Service;

@Service
public class ExampleService extends ServiceBase<Example, Long, ExampleRepository, ExampleRequest, ExampleResponse> {

    public ExampleService(ExampleRepository repository) {
        super(repository);
    }
}
