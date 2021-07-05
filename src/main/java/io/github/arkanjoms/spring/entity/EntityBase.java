package io.github.arkanjoms.spring.entity;

import java.io.Serializable;

public interface EntityBase<ID extends Serializable> extends Serializable {
    ID getId();
}
