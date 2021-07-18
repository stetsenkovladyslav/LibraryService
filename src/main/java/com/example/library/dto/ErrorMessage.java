package com.example.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.List;


@Getter
@Setter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessage {

    private Integer status;
    private String message;
    private Instant timestamp;
    private List<ValidationErrorMessage> validationErrors;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ValidationErrorMessage {
        private String field;
        private String message;
    }
}
