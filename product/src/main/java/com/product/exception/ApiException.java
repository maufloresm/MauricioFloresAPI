package com.product.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    // Código de estado HTTP del error
    private HttpStatus status;
    
    // Constructor que recibe el código de estado y el mensaje del error
    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
    
    // Devuelve el código de estado HTTP del error
    public HttpStatus getStatus() {
        return status;
    }
    
    // Permite cambiar el código de estado 
    public void setStatus(HttpStatus status) {
        this.status = status;
    }
    
    // Retorna el ID de serialización de la clase
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
