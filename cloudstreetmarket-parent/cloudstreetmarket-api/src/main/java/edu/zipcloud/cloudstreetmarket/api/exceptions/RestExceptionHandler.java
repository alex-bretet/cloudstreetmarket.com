package edu.zipcloud.cloudstreetmarket.api.exceptions;

import static org.springframework.http.HttpStatus.*;
import static edu.zipcloud.cloudstreetmarket.core.i18n.I18nKeys.*;

import javax.naming.AuthenticationException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import edu.zipcloud.cloudstreetmarket.core.services.ResourceBundleService;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private ResourceBundleService bundle;
	
    @Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
    	
    	ErrorInfo errorInfo = null;
    	if(body!=null && bundle.containsKey(body.toString())){
    		String key = body.toString();
    		String localizedMessage = bundle.get(key);
    		errorInfo = new ErrorInfo(ex, localizedMessage, key, status);
    	}
    	else{
    		errorInfo = new ErrorInfo(ex, (body!=null)? body.toString() : null, null, status);
    	}

		return new ResponseEntity<Object>(errorInfo, headers, status);
	}
    
    // API

    // 400
    
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        return handleExceptionInternal(ex, I18N_API_GENERIC_REQUEST_BODY_NOT_VALID, headers, BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        return handleExceptionInternal(ex, I18N_API_GENERIC_REQUEST_PARAMS_NOT_VALID, headers, BAD_REQUEST, request);
    }

    @Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, I18N_API_GENERIC_REQUEST_PARAMS_NOT_VALID, headers, BAD_REQUEST, request);
	}

    @ExceptionHandler({ ConstraintViolationException.class, TransactionSystemException.class })
    protected ResponseEntity<Object> handleBeanValidation(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, I18N_API_GENERIC_REQUEST_BODY_NOT_VALID, new HttpHeaders(), BAD_REQUEST, request);
    }
    
    @ExceptionHandler({ InvalidDataAccessApiUsageException.class, DataAccessException.class, IllegalArgumentException.class })
    protected ResponseEntity<Object> handleConflict(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, I18N_API_GENERIC_REQUEST_PARAMS_NOT_VALID, new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler({ BadCredentialsException.class, AuthenticationException.class, AccessDeniedException.class})
    protected ResponseEntity<Object> handleBadCredentials(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, I18N_API_GENERIC_OPERATION_DENIED, new HttpHeaders(), FORBIDDEN, request);
    }
    
    // 403

    
    // 404

    @ExceptionHandler(value = { EntityNotFoundException.class, ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, I18N_API_GENERIC_RESOURCE_NOT_FOUND, new HttpHeaders(), NOT_FOUND, request);
    }

    // 409

    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleBadRequest(final DataIntegrityViolationException ex, final WebRequest request) {
        return handleExceptionInternal(ex, I18N_API_GENERIC_RESOURCE_ALREADY_EXISTS, new HttpHeaders(), CONFLICT, request);
    }

    // 412

    // 500

    @ExceptionHandler({ NullPointerException.class, IllegalStateException.class })
    public ResponseEntity<Object> handleInternal(final RuntimeException ex, final WebRequest request) {
        return handleExceptionInternal(ex, I18N_API_GENERIC_INTERNAL, new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
    }

}
