package edu.zipcloud.cloudstreetmarket.api.exceptions;

import javax.naming.AuthenticationException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.TypeMismatchException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return new ResponseEntity<Object>(new ErrorInfo(ex, (body!=null)? body.toString() : null, status), headers, status);
	}
    
    // API

    // 400
    
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        return handleExceptionInternal(ex, "The provided request body is not readable!", headers, BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        return handleExceptionInternal(ex, "The request parameters were not valid!", headers, BAD_REQUEST, request);
    }

    @Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, "The request parameters were not valid!", headers, BAD_REQUEST, request);
	}

    @ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class, IllegalArgumentException.class })
    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, "The request parameters were not valid!", new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler({ BadCredentialsException.class, AuthenticationException.class })
    protected ResponseEntity<Object> handleBadCredentials(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, "The request parameters were not valid!", new HttpHeaders(), FORBIDDEN, request);
    }
    
    // 403

    
    // 404

    @ExceptionHandler(value = { EntityNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, "Required / requested resource not found!", new HttpHeaders(), NOT_FOUND, request);
    }

    // 409

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleBadRequest(final ConstraintViolationException ex, final WebRequest request) {
        return handleExceptionInternal(ex, "The resource already exists!", new HttpHeaders(), CONFLICT, request);
    }
    
    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex, final WebRequest request) {
        return handleExceptionInternal(ex, "The resource is used in another model!", new HttpHeaders(), CONFLICT, request);
    }

    // 412

    // 500

    @ExceptionHandler({ NullPointerException.class, IllegalStateException.class })
    public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, "An internal error happened during the request! Please try again or contact an administrator.", new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
    }

}
