package com.product.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Representa una categoría de productos en la aplicación.
 * Se almacena en la base de datos bajo la tabla "category".
 */
@Entity
@Table(name = "category")
public class Category {

    @Id
    private int category_id;

    private String category;
    private String tag;
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

    /**
     * Obtiene el identificador único de la categoría.
     * 
     * @return ID de la categoría.
     */
    public int getCategoryId() {
        return category_id;
    }

    /**
     * Devuelve el nombre de la categoría.
     * 
     * @return Nombre de la categoría.
     */
    public String getCategory() {
        return category;
    }

    /**
     * Recupera el tag de la categoría.
     * 
     * @return Tag de la categoría.
     */
    public String getTag() {
        return tag;
    }

    /**
     * Indica si la categoría está activa o inactiva.
     * 
     * @return Estado actual de la categoría (1 = activa, 0 = inactiva).
     */
    public int getStatus() {
        return status;
    }

    /**
     * Alterna el estado de la categoría entre activo (1) e inactivo (0).
     */
    public void changeStatus() {
        this.status = (this.status == 1) ? 0 : 1;
    }

    /**
     * Asigna un nuevo estado a la categoría.
     * 
     * @param i Nuevo estado (1 para activo, 0 para inactivo).
     */
    public void setStatus(int i) {
        this.status = i;
    }

    /**
     * Representación en cadena del objeto Category.
     * Muestra los valores de los atributos en un formato legible.
     * 
     * @return Cadena con los valores de la categoría.
     */
    @Override
    public String toString() {
        return String.format("{ID: %d, Categoría: %s, Tag: %s, Estado: %d}", 
                              category_id, category, tag, status);
    }
}
