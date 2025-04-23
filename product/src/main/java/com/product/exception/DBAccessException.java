package com.product.exception;

import org.springframework.dao.DataAccessException;

public class DBAccessException extends RuntimeException {
    
    private static final long serialVersionUID = 1L; // Identificador único de la clase
    
    private DataAccessException exception; // Excepción original de la base de datos
    
    // Constructor: recibe una excepción de acceso a datos y la guarda
    public DBAccessException(DataAccessException e) {
        this.exception = e;
    }

    // Devuelve la excepción almacenada (por si queremos inspeccionarla)
    public DataAccessException getException() {
        return exception;
    }

    // Permite cambiar la excepción guardada (rara vez se usa)
    public void setException(DataAccessException exception) {
        this.exception = exception;
    }

    // Devuelve el ID único de la clase (normalmente no hace falta usarlo)
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
