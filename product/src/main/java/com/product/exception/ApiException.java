package com.product.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L; // Identificador único de la clase

    private HttpStatus status; // Código de estado HTTP del error
    
    // Constructor: recibe el código de estado y un mensaje de error
    public ApiException(HttpStatus status, String message) {
        super(message); // Llama al constructor de RuntimeException con el mensaje
        this.status = status;
    }
    
    // Devuelve el código de estado HTTP asociado al error
    public HttpStatus getStatus() {
        return status;
    }
    
    // Permite cambiar el código de estado del error (por si se necesita modificar)
    public void setStatus(HttpStatus status) {
        this.status = status;
    }
    
    // Retorna el identificador único de la clase (rara vez se usa directamente)
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
