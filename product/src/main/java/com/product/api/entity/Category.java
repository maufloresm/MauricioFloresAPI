package com.product.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Representa una categoría de productos en la aplicación.
 * Se almacena en la base de datos bajo la tabla "category".
 */
@Entity
@Table(name = "category")
public class Category {

    @Id
    @Column(name = "category_id")
    @JsonProperty("category_id") // Para serializar el campo como category_id en JSON
    private int category_id;

    @Column(name = "category", nullable = false)
    @JsonProperty("category") // Para serializar el campo como category en JSON
    private String category;

    @Column(name = "tag", nullable = false, unique = true) // tag no puede ser nulo ni repetido
    @JsonProperty("tag") // Para serializar el campo como tag en JSON
    private String tag;

    @Column(name = "status")
    @JsonProperty("status")
    private int status = 1;

    private static int idCounter = 0;

    /**
     * Constructor sin parámetros.
     * Asigna valores por defecto a los atributos.
     */
    public Category() {
        this.category_id = ++idCounter;
        this.category = "Cat" + this.category_id;
        this.tag = "tag" + this.category_id;
    }

    /**
     * Constructor con parámetros personalizados.
     * Permite inicializar la categoría con un nombre y un tag específico.
     * 
     * @param category Nombre de la categoría.
     * @param tag Identificador único asociado a la categoría.
     */
    public Category(String category, String tag) {
        this.category_id = ++idCounter;
        this.category = category;
        this.tag = tag;
    }

    // Getters y setters para los atributos

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

    /**
     * Alterna el estado de la categoría entre activo (1) e inactivo (0).
     */
    public void changeStatus() {
        this.status = (this.status == 1) ? 0 : 1;
    }

    @Override
    public String toString() {
        return String.format("{ID: %d, Categoría: %s, Tag: %s, Estado: %d}", 
                              category_id, category, tag, status);
    }
}