package com.kathesama.app.service.infrastructure.adapter.input.rest.controller;

import com.kathesama.app.service.domain.exception.ProductNotFoundException;
import com.kathesama.app.service.domain.exception.UserNotFoundException;
import com.kathesama.app.service.infrastructure.adapter.input.rest.dto.model.response.ErrorResponse;
import com.kathesama.app.service.util.common.ErrorCatalog;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.kathesama.app.service.util.common.ErrorCatalog.*;

@RestControllerAdvice
public class GlobalControllerAdvice {
    private static final Map<Class<? extends Exception>, HttpStatus> STATUS_CODE_MAPPING = new HashMap<>();
    private static final Map<Class<? extends Exception>, ErrorCatalog> ERROR_CODE_MAPPING = new HashMap<>();

    static {
        ERROR_CODE_MAPPING.put(ProductNotFoundException.class, PRODUCT_NOT_FOUND);
        ERROR_CODE_MAPPING.put(UserNotFoundException.class, USER_NOT_FOUND);
        ERROR_CODE_MAPPING.put(MethodArgumentNotValidException.class, INVALID_PARAMS);
        ERROR_CODE_MAPPING.put(AccessDeniedException.class, ACCESS_DENIED);
    }

    static {
        STATUS_CODE_MAPPING.put(ProductNotFoundException.class, HttpStatus.NOT_FOUND);
        STATUS_CODE_MAPPING.put(UserNotFoundException.class, HttpStatus.NOT_FOUND);
        STATUS_CODE_MAPPING.put(MethodArgumentNotValidException.class, HttpStatus.BAD_REQUEST);
        STATUS_CODE_MAPPING.put(AccessDeniedException.class, HttpStatus.UNAUTHORIZED);
    }

    private static ErrorResponse createErrorResponse(ErrorCatalog errorType) {
        return ErrorResponse.builder()
                .code(errorType.getCode())
                .message(errorType.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    /************* INTERNAL ERROR HANDLER */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericError(Exception exception) {

        ErrorResponse errorResponse = createErrorResponse(GENERIC_ERROR);
        errorResponse.setDetails(Collections.singletonList(exception.getMessage()));
        return errorResponse;
    }

    /************* Products, Users, access and Given params ERROR HANDLER */
    @ExceptionHandler({
            AccessDeniedException.class,
            ProductNotFoundException.class,
            UserNotFoundException.class,
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<ErrorResponse> handleGeneralNotFoundException(Exception exception) {
        ErrorCatalog response = ERROR_CODE_MAPPING.get(exception.getClass());
        HttpStatus statusCode = STATUS_CODE_MAPPING.getOrDefault(exception.getClass(), HttpStatus.INTERNAL_SERVER_ERROR);

        ErrorResponse errorResponse = createErrorResponse(response);

        if (statusCode.equals(HttpStatus.BAD_REQUEST) && exception instanceof MethodArgumentNotValidException methodargumentnotvalidexception){
            BindingResult result = methodargumentnotvalidexception.getBindingResult();
            List<String> details = result
                    .getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            errorResponse.setDetails(details);
        }

        return new ResponseEntity<>(errorResponse, statusCode);
    }
}
