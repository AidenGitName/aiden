package com.wonders.hms.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;

@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandling {
    @ExceptionHandler(value = { IOException.class })
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    protected ErrorMessage handleIOException(IOException ex) {

        if (StringUtils.containsIgnoreCase(ExceptionUtils.getRootCauseMessage(ex), "Broken pipe")) {
            // socket is closed, cannot return any response
            return null;
        }

        ErrorMessage errorMessage = new ErrorMessage();
        log.warn(ex.getMessage(), ex);

        return errorMessage;
    }

    @ExceptionHandler(value = { HttpClientErrorException.class })
    @ResponseBody
    protected ResponseEntity<?> badRequestError(HttpClientErrorException ex) {

        ErrorMessage errorMessage = new ErrorMessage();
        log.warn(ex.getMessage(), ex);

        return new ResponseEntity<>(errorMessage, ex.getStatusCode());
    }

    @ExceptionHandler(value = { Exception.class })
    @ResponseBody
    protected ResponseEntity<?> commonException(Exception ex) {

        ErrorMessage errorMessage = new ErrorMessage();
        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
