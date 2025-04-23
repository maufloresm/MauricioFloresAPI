package com.product.exception;

import java.time.*;

import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

// Clase que representa la estructura de una respuesta de error en la aplicación
public class ExceptionResponse {

    // Formato de fecha para el timestamp (año-mes-día hora:minuto:segundo)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timestamp; // Fecha y hora en que ocurrió el error
    
    private Integer status; // Código de estado HTTP (ejemplo: 404, 500)
    
    private HttpStatus error; // Tipo de error HTTP (ejemplo: NOT_FOUND, BAD_REQUEST)
    
    private String message; // Mensaje descriptivo del error
    
    private String path; // Ruta de la petición donde se produjo el error
    
    // Constructor vacío para facilitar la serialización/deserialización
    public ExceptionResponse() {}

    // Métodos getter y setter para acceder y modificar los atributos de la clase
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public HttpStatus getError() {
        return error;
    }
    
    public void setError(HttpStatus error) {
        this.error = error;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getPath() {
        return path;
    }
    
    public void setPath(String path) {
        this.path = path;
    }
}
