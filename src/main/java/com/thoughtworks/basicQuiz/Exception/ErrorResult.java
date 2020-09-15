package com.thoughtworks.basicQuiz.Exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
