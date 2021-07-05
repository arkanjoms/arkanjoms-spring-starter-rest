package io.github.arkanjoms.spring.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.CONFLICT;

@ResponseStatus(code = CONFLICT, reason = "path id is not equals to data id")
public class IdentifierNotEqualsException extends RuntimeException {
}
