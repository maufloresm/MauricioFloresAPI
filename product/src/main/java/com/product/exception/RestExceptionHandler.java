package com.product.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// Clase que maneja las excepciones de manera global en la API
@ControllerAdvice 
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
    // Maneja excepciones de tipo ApiException (errores personalizados de la API)
    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ExceptionResponse> handleApiException(ApiException exception, WebRequest request) {
        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(LocalDateTime.now()); // Captura la fecha y hora del error
        response.setStatus(exception.getStatus().value()); // Obtiene el código de estado HTTP
        response.setError(exception.getStatus()); // Guarda el tipo de error HTTP
        response.setMessage(exception.getMessage()); // Asigna el mensaje de error
        response.setPath(((ServletWebRequest) request).getRequest().getRequestURI().toString()); // Guarda la URL donde ocurrió el error
        return new ResponseEntity<>(response, response.getError());
    }
	
    // Maneja errores relacionados con el acceso a la base de datos
    @ExceptionHandler(DBAccessException.class)
    protected ResponseEntity<ExceptionResponse> DBAccessException(DBAccessException exception, WebRequest request) {

        System.out.println(exception.getException().getLocalizedMessage()); // Imprime el error en la consola para depuración
		
        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(LocalDateTime.now()); // Registra el momento del error
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); // Código de estado 500 (error interno del servidor)
        response.setError(HttpStatus.INTERNAL_SERVER_ERROR); // Tipo de error HTTP
        response.setMessage("Error al consultar la base de datos"); // Mensaje genérico de error en base de datos
        response.setPath(((ServletWebRequest) request).getRequest().getRequestURI().toString()); // Ruta donde ocurrió el error
		
        return new ResponseEntity<>(response, response.getError());
    }
}
