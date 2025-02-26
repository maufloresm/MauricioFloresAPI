package com.product.api.entity;

import java.util.Objects;

public class Category {
	private int category_id;
    private String category;
    private String tag;
    private int status;
	
	public Category(int category_id, String category, String tag, int status) {
        this.category_id = category_id;
        this.category = category;
        this.tag = tag;
        this.status = status;
    }
	
	//Getters
    public Integer getCategoryId() {
        return category_id;
    }

    public String getCategoria() {
        return category;
    }

    public String getTag() {
        return tag;
    }

    public Integer getStatus() {
        return status;
    }

    //Setter
    public void setStatus(Integer status) {
        this.status = status;
    }
	


    // Sobrescribimos toString para mostrar la categoría
    @Override
    public String toString() {
        return "(" + category_id + ", \"" + category + "\", \"" + tag + "\", " + status + ")";
    }

    // Sobrescribimos equals para comparar por ID, nombre o tag
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Category other = (Category) obj;
        return category_id == other.category_id
                || category.equals(other.category)
                || tag.equals(other.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category_id, category, tag);
    }
    
}
