package io.github.arkanjoms.spring.exampleapp.dto;

import io.github.arkanjoms.spring.dto.RequestBase;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExampleRequest implements RequestBase<Long> {
    private Long id;
    private String name;
}
