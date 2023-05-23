package com.disquesea.disqueseaapi.web.rest.controllers.exception.handler;

import com.disquesea.disqueseaapi.domain.exceptions.BusinessException;
import com.disquesea.disqueseaapi.domain.exceptions.EntityIsBeingUsedException;
import com.disquesea.disqueseaapi.domain.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@ControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;
    private final String GENERIC_USER_MESSAGE = "An internal system error has occurred. Try again after.";


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusiness(BusinessException ex, WebRequest request) {
        final HttpStatus status = HttpStatus.BAD_REQUEST;

        Problem body = createProblemBuilder(status, ProblemType.ERROR_BUSINESS, ex.getMessage())
                .userMessage(ex.getMessage()).build();

        return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityIsBeingUsedException.class)
    public ResponseEntity<?> handleEntityIsBeingUsed(EntityIsBeingUsedException ex, WebRequest request) {
        final HttpStatus status = HttpStatus.CONFLICT;

        Problem body = createProblemBuilder(status, ProblemType.ENTITY_IS_BEING_USED, ex.getMessage())
                .userMessage(ex.getMessage()).build();

        return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        final HttpStatus status = HttpStatus.NOT_FOUND;

        Problem body = createProblemBuilder(status, ProblemType.RESOURCE_NOT_FOUND, ex.getMessage())
                .userMessage(ex.getMessage()).build();

        return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        final HttpStatus status = HttpStatus.valueOf(statusCode.value());

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, new HttpHeaders(), statusCode, request);

        } else if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException(
                    (InvalidFormatException) rootCause, new HttpHeaders(), statusCode, request);
        }

        final String detail = "Payload has a syntax error";
        Problem body = createProblemBuilder(status, ProblemType.MESSAGE_NOT_READABLE, detail)
                .userMessage(GENERIC_USER_MESSAGE)
                .build();

        return handleExceptionInternal(ex, body, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(
            PropertyBindingException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        final HttpStatus status = HttpStatus.valueOf(statusCode.value());

        final String path = joinPath(ex.getPath());
        final String details = String.format("There is not property '%s' to be informed", path);

        Problem body = createProblemBuilder(status, ProblemType.MESSAGE_NOT_READABLE, details)
                .userMessage(GENERIC_USER_MESSAGE)
                .build();

        return handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(
            InvalidFormatException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        final HttpStatus status = HttpStatus.valueOf(statusCode.value());

        final String path = joinPath(ex.getPath());

        final String details = String.format(
                "The property '%s' received an invalid value '%s' instead of a with the correct type '%s'",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        Problem body = createProblemBuilder(status, ProblemType.MESSAGE_NOT_READABLE, details)
                .userMessage(GENERIC_USER_MESSAGE)
                .build();

        return handleExceptionInternal(ex, body, headers, statusCode, request);
    }
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers, statusCode, request);
        }

        return handleExceptionInternal(ex, null, headers, statusCode, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        final HttpStatus status = HttpStatus.valueOf(statusCode.value());

        final String detail = String.format(
                "URL parameter '%s' received an invalid value '%s' instead of a with the correct type '%s'",
                ex.getPropertyName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        Problem body = createProblemBuilder(status, ProblemType.INVALID_PARAMETER, detail)
                .userMessage(GENERIC_USER_MESSAGE)
                .build();

        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        final HttpStatus status = HttpStatus.valueOf(statusCode.value());

        final String detail = String.format("There is not resource '%s'", ex.getRequestURL());

        Problem body = createProblemBuilder(status, ProblemType.RESOURCE_NOT_FOUND, detail)
                .userMessage(GENERIC_USER_MESSAGE)
                .build();

        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        final HttpStatus status = HttpStatus.valueOf(statusCode.value());

        final String detail = "There is at least an invalid data field.";

        BindingResult bindingResult = ex.getBindingResult();

        List<Problem.Field> problemFields = bindingResult.getFieldErrors().stream()
                .map(fieldError -> {
                    final String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

                    return Problem.Field.builder()
                            .name(fieldError.getField())
                            .userMessage(message)
                            .build();
                })
                .toList();

        Problem body = createProblemBuilder(status, ProblemType.INVALID_DATA, detail)
                .userMessage(detail)
                .violations(problemFields)
                .build();

        return handleExceptionInternal(ex, body, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerUnexpectedException(Exception ex, WebRequest request){
        final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ex.printStackTrace();

        Problem body = createProblemBuilder(status, ProblemType.SYSTEM_ERROR, GENERIC_USER_MESSAGE)
                .userMessage(GENERIC_USER_MESSAGE)
                .build();

        return handleExceptionInternal(ex, body, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        final int status = statusCode.value();

        if (isNull(body)) {
            final String message = HttpStatus.resolve(status).getReasonPhrase();
            body = Problem.builder()
                    .timestamp(LocalDateTime.now())
                    .status(status)
                    .title(message)
                    .userMessage(GENERIC_USER_MESSAGE)
                    .build();
        }

        ex.printStackTrace();

        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus statusCode, ProblemType problemType, String detail) {
        final int status = statusCode.value();

        return Problem.builder()
                .timestamp(LocalDateTime.now())
                .detail(detail)
                .title(problemType.getTitle())
                .status(status)
                .type(problemType.getUri());
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
    }
}





















