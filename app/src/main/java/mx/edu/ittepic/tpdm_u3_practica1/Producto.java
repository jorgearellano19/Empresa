package mx.edu.ittepic.tpdm_u3_practica1;

/**
 * Created by jorgearellano on 29/03/17.
 */

public class Producto {
    int id,stack;
    String nombre;
    float precio;

    public Producto(int id, String nombre, float precio, int stack) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stack = stack;
    }
}
