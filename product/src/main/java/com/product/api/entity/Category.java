package com.product.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

// Esta clase representa una categoría de productos en la base de datos
@Entity
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "category_id")
    @JsonProperty("category_id")
    private int category_id;

    @Column(name = "category", nullable = false)
    @JsonProperty("category")
    private String category;

    @Column(name = "tag", nullable = false, unique = true)
    @JsonProperty("tag")
    private String tag;

    @Column(name = "status")
    @JsonProperty("status")
    private int status = 1;

    private static int idCounter = 0;

    // Constructor por defecto: crea una categoría con valores generados automáticamente
    public Category() {
        this.category_id = ++idCounter;
        this.category = "Cat" + this.category_id;
        this.tag = "tag" + this.category_id;
    }

    // Constructor personalizado: permite definir una categoría con nombre y tag específicos
    public Category(String category, String tag) {
        this.category_id = ++idCounter;
        this.category = category;
        this.tag = tag;
    }

    // Métodos para obtener y establecer valores de los atributos
    public int getCategoryId() {
        return category_id;
    }

    public void setCategoryId(int category_id) {
        this.category_id = category_id;
    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // Método para alternar el estado de la categoría entre activo e inactivo
    public void changeStatus() {
        this.status = (this.status == 1) ? 0 : 1;
    }

    // Representación en cadena de la categoría
    @Override
    public String toString() {
        return String.format("{ID: %d, Categoría: %s, Tag: %s, Estado: %d}", 
                              category_id, category, tag, status);
    }
}
