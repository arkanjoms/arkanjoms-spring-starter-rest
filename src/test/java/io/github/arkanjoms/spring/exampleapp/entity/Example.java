package io.github.arkanjoms.spring.exampleapp.entity;

import io.github.arkanjoms.spring.entity.EntityBase;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Example implements EntityBase<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(insertable = false, updatable = false)
    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
