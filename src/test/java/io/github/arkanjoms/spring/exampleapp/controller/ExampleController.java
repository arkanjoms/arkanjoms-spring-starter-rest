package io.github.arkanjoms.spring.exampleapp.controller;

import io.github.arkanjoms.spring.exampleapp.dto.ExampleRequest;
import io.github.arkanjoms.spring.exampleapp.dto.ExampleResponse;
import io.github.arkanjoms.spring.rest.ControllerBase;
import io.github.arkanjoms.spring.service.ServiceBase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/examples")
public class ExampleController extends ControllerBase<ExampleRequest, ExampleResponse, Long> {

    public ExampleController(ServiceBase service) {
        super(service);
    }
}
