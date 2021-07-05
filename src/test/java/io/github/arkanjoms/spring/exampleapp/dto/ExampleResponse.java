package io.github.arkanjoms.spring.exampleapp.dto;

import io.github.arkanjoms.spring.dto.ResponseBase;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExampleResponse implements ResponseBase<Long> {
    private Long id;
    private String name;
}
