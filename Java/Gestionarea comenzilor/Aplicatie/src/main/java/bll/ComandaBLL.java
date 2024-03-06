package bll;

import dao.ComandaDAO;
import model.Client;
import model.Comanda;

import java.util.List;
import java.util.NoSuchElementException;

public class ComandaBLL {
    private ComandaDAO comandaDAO;

    /**
     * Creează o instanță a clasei ComandaBLL și initializează obiectul de acces la date ComandaDAO.
     */
    public ComandaBLL() {
        comandaDAO=new ComandaDAO();
    }


    /**
     * Găsește o comandă după ID.
     *
     * @param id ID-ul comenzii căutate.
     * @return Comanda găsită.
     * @throws NoSuchElementException dacă comanda nu a fost găsită în baza de date.
     */
    public Comanda findComandaById(int id){
        Comanda comanda =comandaDAO.findById(id, "idComanda");
        if (comanda == null) {
            throw new NoSuchElementException("Comanda cu acest id ("+ id + ") nu s-a gasit!");
        }
        return comanda;
    }


    /**
     * Returnează o listă cu toate comenzile existente în baza de date.
     *
     * @return Lista de comenzi.
     * @throws NoSuchElementException dacă nu există comenzi în listă.
     */
    public List<Comanda> findAllOrders(){
        List<Comanda> comenzi =comandaDAO.findAll();
        if (comenzi == null) {
            throw new NoSuchElementException("Nu exista comenzile in lista!");
        }
        return comenzi;
    }

    /**
     * Inserează o comandă în baza de date.
     *
     * @param c Comanda de inserat.
     * @return Comanda inserată.
     * @throws NoSuchElementException dacă nu se poate insera comanda.
     */
    public Comanda insertComanda(Comanda c){
        Comanda comanda=comandaDAO.insert(c);
        if (comanda == null) {
            throw new NoSuchElementException("Nu se poate insera comanda!");
        }
        return comanda;
    }

    /**
     * Caută id-ul maxim al comenzii, care reprezintă ultima comanda inserată, pentru a-i genera factura.
     * @return id-ul ultimei comenzi inserate
     */

    public int maxIdComanda(){
        int id=comandaDAO.maxId("idComanda");
        return id;
    }

}
