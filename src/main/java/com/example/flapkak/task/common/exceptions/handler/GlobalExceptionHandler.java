package com.example.flapkak.task.common.exceptions.handler;

import com.example.flapkak.task.common.exceptions.BadRequestException;
import com.example.flapkak.task.common.exceptions.InternalServerException;
import com.example.flapkak.task.common.exceptions.NotFoundException;
import com.example.flapkak.task.common.exceptions.UnauthorizedException;
import com.example.flapkak.task.dtos.ErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = {InternalServerException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDto handleException(InternalServerException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDto.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message("Unexpected Error!")
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleException(BadRequestException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDto.builder()
                .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(exception.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorDto handleException(UnauthorizedException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDto.builder()
                .code(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .message(exception.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto handleException(NotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ErrorDto.builder()
                .code(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(exception.getMessage())
                .build();
    }

    @ResponseBody
    @ExceptionHandler(value = {ValidationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDto handleException(ValidationException validationException) {
        ErrorDto errorDTO;
        if(validationException instanceof ConstraintViolationException){
            String violations = extractViolationsFromException((ConstraintViolationException) validationException);
            log.error(violations, validationException);
            errorDTO = ErrorDto.builder()
                    .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .message(violations)
                    .build();
        } else {
            String exceptionMessage = validationException.getMessage();
            log.error(exceptionMessage, validationException);
            errorDTO = ErrorDto.builder()
                    .code(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .message(exceptionMessage)
                    .build();
        }
        return errorDTO;
    }

    private String extractViolationsFromException(ConstraintViolationException validationException) {
        return validationException.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("--"));
    }
}
