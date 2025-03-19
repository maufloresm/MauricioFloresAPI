package com.product.exception;

import org.springframework.dao.DataAccessException;

public class DBAccessException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    // Guarda la excepción original generada en la base de datos
    private DataAccessException exception;
    
    // Constructor que recibe y almacena la excepción
    public DBAccessException(DataAccessException e) {
        this.exception = e;
    }

    // Devuelve la excepción almacenada
    public DataAccessException getException() {
        return exception;
    }

    // Permite cambiar la excepción (no suele ser necesario)
    public void setException(DataAccessException exception) {
        this.exception = exception;
    }

    // Retorna el ID de serialización de la clase
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
