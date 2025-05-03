package com.product.api.domain;
import java.io.Serializable;

  // Muestra un registro en la tabla usuario

public class Usuario implements Serializable {
	
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;  
    private String role;      
    private int status;      

    public Usuario(String username, String password, String role, int status) {
        this.username = username;
        this.password = password;
        this.role     = role;
        this.status   = status;
    }

    public String getUsername(){
    	return username;
    }
    
    public String getPassword(){
    	return password; 
    }
    
    public String getRole(){ 
    	return role; 
    }
    
    public int getStatus(){
    	return status;
    }
    
}
