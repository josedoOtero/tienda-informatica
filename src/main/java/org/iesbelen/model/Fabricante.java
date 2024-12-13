package org.iesbelen.model;


import java.util.Collection;

public class Fabricante {

    private int idFabricante;
    private String nombre;

    public int getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(int idFabricante) {
        this.idFabricante = idFabricante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fabricante that = (Fabricante) o;

        if (idFabricante != that.idFabricante) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idFabricante;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        return result;
    }

}
