package com.thoughtworks.basicQuiz.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResult {
    private Date timestamp;
    private Integer code;
    private String error;
    private String message;
}
