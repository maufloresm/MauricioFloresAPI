package com.product.exception;

import java.time.*;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ExceptionResponse {

    // Formato de fecha para el timestamp (año-mes-día hora:minuto:segundo)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp;
    
    // Código de estado HTTP (numérico)
    private Integer status;
    
    // Tipo de error HTTP (ej. NOT_FOUND, BAD_REQUEST)
    private HttpStatus error;
    
    // Mensaje de error que describe el problema
    private String message;
    
    // Ruta de la petición donde ocurrió el error
    private String path;
    
    // Constructor vacío
    public ExceptionResponse() {}

    // Devuelve el timestamp del error
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    // Establece el timestamp del error
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    // Devuelve el código de estado HTTP
    public Integer getStatus() {
        return status;
    }
    
    // Establece el código de estado HTTP
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    // Devuelve el tipo de error HTTP
    public HttpStatus getError() {
        return error;
    }
    
    // Establece el tipo de error HTTP
    public void setError(HttpStatus error) {
        this.error = error;
    }
    
    // Devuelve el mensaje de error
    public String getMessage() {
        return message;
    }
    
    // Establece el mensaje de error
    public void setMessage(String message) {
        this.message = message;
    }
    
    // Devuelve la ruta donde ocurrió el error
    public String getPath() {
        return path;
    }
    
    // Establece la ruta donde ocurrió el error
    public void setPath(String path) {
        this.path = path;
    }
}
