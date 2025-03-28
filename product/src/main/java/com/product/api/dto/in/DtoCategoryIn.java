package com.product.api.dto.in;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;


public class DtoCategoryIn {
	
	
	@JsonProperty("category")
	@NotNull(message = "La región es necesaria.")
	private String category;

 
	@JsonProperty("tag")
	@NotNull(message = "El tag es necesario. ")
	private String tag;
	
	// Getters y Setters 

   
	public String getCategory() {
		return category;
	}

   
	public void setCategory(String category) {
		this.category = category;
	}


	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}
		
}
