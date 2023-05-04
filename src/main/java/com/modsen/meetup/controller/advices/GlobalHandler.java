package com.modsen.meetup.controller.advices;

import com.modsen.meetup.dto.error.FieldError;
import com.modsen.meetup.dto.error.MultiplyError;
import com.modsen.meetup.dto.error.SingleError;
import org.postgresql.util.PSQLException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SingleError handle(IllegalArgumentException e) {

        return new SingleError(e.getMessage());
    }

    @ExceptionHandler(NoResultException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SingleError handle(NoResultException e) {

        return new SingleError(e.getMessage());
    }

    @ExceptionHandler(PSQLException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SingleError handle(PSQLException e) {

        return new SingleError(e.getServerErrorMessage().getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MultiplyError handle(ConstraintViolationException e) {
        List<FieldError> fieldError = new ArrayList<>();

        String field = null;

        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            for (Path.Node node : constraintViolation.getPropertyPath()) {
                field = node.getName();
            }
            fieldError.add(new FieldError(constraintViolation.getMessage(), field));
        }

        return new MultiplyError(fieldError);
    }

}
