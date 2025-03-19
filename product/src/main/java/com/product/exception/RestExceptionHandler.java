package com.product.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice // Esta clase maneja las excepciones globalmente en la API
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
    // Maneja las excepciones personalizadas de tipo ApiException
    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ExceptionResponse> handleApiException(ApiException exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(LocalDateTime.now()); // Marca de tiempo del error
        response.setStatus(exception.getStatus().value()); // Código HTTP del error
        response.setError(exception.getStatus()); // Tipo de error HTTP
        response.setMessage(exception.getMessage()); // Mensaje del error
        response.setPath(((ServletWebRequest) request).getRequest().getRequestURI().toString()); // Ruta de la petición
        return new ResponseEntity<>(response, response.getError());
    }
	
    // Maneja errores al acceder a la base de datos
    @ExceptionHandler(DBAccessException.class)
    protected ResponseEntity<ExceptionResponse> DBAccessException(DBAccessException exception, WebRequest request) {

        System.out.println(exception.getException().getLocalizedMessage()); // Muestra el error en consola
		
        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); // Código 500
        response.setError(HttpStatus.INTERNAL_SERVER_ERROR); // Tipo de error
        response.setMessage("Error al consultar la base de datos"); // Mensaje de error
        response.setPath(((ServletWebRequest) request).getRequest().getRequestURI().toString());
		
        return new ResponseEntity<>(response, response.getError());
    }
}
