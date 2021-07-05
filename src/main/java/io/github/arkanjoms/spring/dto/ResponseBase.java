package io.github.arkanjoms.spring.dto;

import java.io.Serializable;

public interface ResponseBase<I extends Serializable> extends Serializable {
    I getId();
}
