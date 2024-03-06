package bll;

import dao.FacturaDAO;
import model.Client;
import model.Factura;

import java.util.NoSuchElementException;

public class FacturaBLL {
    private FacturaDAO facturaDAO;

    /**
     * Creează o instanță a clasei FacturaBLL și initializează obiectul de acces la date FacturaDAO.
     */
    public FacturaBLL() {
        facturaDAO=new FacturaDAO();
    }

    /**
     * Inserează o factură în baza de date.
     *
     * @param f Factura de inserat.
     * @return Factura inserată.
     * @throws NoSuchElementException dacă nu se poate insera factura.
     */
    public Factura insertFactura(Factura f){
        Factura factura=facturaDAO.insert(f);
        if (factura == null) {
            throw new NoSuchElementException("Nu se poate insera factura!");
        }
        return factura;
    }
}
