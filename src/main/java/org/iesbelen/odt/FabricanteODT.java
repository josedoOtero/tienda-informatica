package org.iesbelen.odt;

import org.iesbelen.dao.FabricanteDAO;
import org.iesbelen.dao.FabricanteDAOImpl;
import org.iesbelen.model.Fabricante;

public class FabricanteODT extends Fabricante {
    private Integer numeroProductor = 0;

    public FabricanteODT() {
    }

    public FabricanteODT(Fabricante f) {
        FabricanteDAO fabDAO = new FabricanteDAOImpl();

        this.setNombre(f.getNombre());
        this.setIdFabricante(f.getIdFabricante());
        this.numeroProductor = fabDAO.getCountProductos(f.getIdFabricante()).orElse(0);
    }

    public Integer getNumeroProductor() {
        return numeroProductor;
    }

    public void setNumeroProductor(Integer numeroProductor) {
        this.numeroProductor = numeroProductor;
    }
}
