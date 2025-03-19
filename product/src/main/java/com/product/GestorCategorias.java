package com.product;

import java.util.ArrayList;
import java.util.List;

import com.product.api.entity.Category;

/**
 * Clase encargada de gestionar las categorías de productos.
 */
public class GestorCategorias {

    // Lista donde se almacenan las categorías registradas
    private static final List<Category> listaCategorias = new ArrayList<>();

    /**
     * Constructor vacío de la clase.
     */
    public GestorCategorias() {}

    /**
     * Agrega una nueva categoría si no existe previamente.
     *
     * @param nuevaCategoria Objeto de tipo Category a agregar.
     */
    public void agregarCategoria(Category nuevaCategoria) {
        if (!existeCategoria(nuevaCategoria.getCategory(), nuevaCategoria.getTag())) {
            listaCategorias.add(nuevaCategoria);
        } else {
            System.out.println("Error: La categoría o el tag ya están registrados.");
        }
    }

    /**
     * Verifica si una categoría o su tag ya existen en la lista.
     *
     * @param nombre Nombre de la categoría.
     * @param etiqueta Identificador o tag de la categoría.
     * @return true si ya existe, false en caso contrario.
     */
    private static boolean existeCategoria(String nombre, String etiqueta) {
        for (Category categoria : listaCategorias) {
            if (categoria.getCategory().equalsIgnoreCase(nombre) 
                    || categoria.getTag().equalsIgnoreCase(etiqueta)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene todas las categorías activas.
     *
     * @return Lista de categorías cuyo estado es activo.
     */
    public List<Category> obtenerCategoriasActivas() {
        List<Category> categoriasActivas = new ArrayList<>();
        for (Category categoria : listaCategorias) {
            if (categoria.getStatus() == 1) {
                categoriasActivas.add(categoria);
            }
        }
        return categoriasActivas;
    }

    /**
     * Marca una categoría como inactiva según su ID.
     *
     * @param id Identificador de la categoría a eliminar.
     */
    public void eliminarCategoria(int id) {
        for (Category categoria : listaCategorias) {
            if (categoria.getCategoryId() == id) {
                categoria.changeStatus();
            }
        }
    }

    /**
     * Método de prueba que muestra el flujo de gestión de categorías.
     */
    public static void main(String[] args) {
        GestorCategorias gestor = new GestorCategorias();

        // Registro de nuevas categorías
        gestor.agregarCategoria(new Category("Utensilios", "utl"));
        gestor.agregarCategoria(new Category("Electrodomésticos", "elm"));
        gestor.agregarCategoria(new Category("Ingredientes", "ing"));
        gestor.agregarCategoria(new Category("Recetas", "rec"));

        // Intento de agregar una categoría duplicada
        gestor.agregarCategoria(new Category("Utensilios", "utl"));

        // Mostrar categorías activas
        System.out.println("Categorías activas antes de eliminación: " + gestor.obtenerCategoriasActivas());

        // Eliminación de una categoría por ID
        gestor.eliminarCategoria(3);

        // Mostrar categorías activas después de la eliminación
        System.out.println("Categorías activas después de eliminación: " + gestor.obtenerCategoriasActivas());

        // Agregar una nueva categoría y mostrar la lista final
        gestor.agregarCategoria(new Category("Condimentos", "cnd"));
        System.out.println("Lista final de categorías activas: " + gestor.obtenerCategoriasActivas());
    }
}
