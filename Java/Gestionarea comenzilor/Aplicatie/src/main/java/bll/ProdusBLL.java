package bll;

import dao.ProdusDAO;
import model.Client;
import model.Produs;

import java.util.List;
import java.util.NoSuchElementException;

public class ProdusBLL {
    private ProdusDAO produsDAO;

    /**
     * Creează o instanță a clasei ProdusBLL și initializează obiectul de acces la date ProdusDAO.
     */
    public ProdusBLL() {
        produsDAO=new ProdusDAO();
    }

    /**
     * Găsește un produs după ID.
     *
     * @param id ID-ul produsului căutat.
     * @return Produsul găsit.
     * @throws NoSuchElementException dacă produsul nu poate fi găsit.
     */
    public Produs findProdusById(int id){
        Produs produs=produsDAO.findById(id, "idProdus");
        if (produs == null) {
            throw new NoSuchElementException("Produsul cu acest id ("+ id + ") nu s-a gasit!");
        }
        return produs;
    }

    /**
     * Returnează toate produsele din sistem.
     *
     * @return Lista de produse.
     * @throws NoSuchElementException dacă nu există produse în listă.
     */
    public List<Produs> findAllProducts(){
        List<Produs> produse=produsDAO.findAll();
        if (produse == null) {
            throw new NoSuchElementException("Nu exista produse in lista!");
        }
        return produse;
    }

    /**
     * Inserează un produs în baza de date.
     *
     * @param p Produsul de inserat.
     * @return Produsul inserat.
     * @throws NoSuchElementException dacă nu se poate insera produsul.
     */
    public Produs insertProdus(Produs p){
        Produs produs=produsDAO.insert(p);
        if (produs == null) {
            throw new NoSuchElementException("Nu se poate insera produsul!");
        }
        return produs;
    }

    /**
     * Actualizează un produs în baza de date.
     *
     * @param p Produsul de actualizat.
     * @param id ID-ul produsului de actualizat.
     * @return Produsul actualizat.
     * @throws NoSuchElementException dacă nu se poate actualiza produsul.
     */
    public Produs updateProdus(Produs p, int id){
        Produs produs=produsDAO.update(p,id,"idProdus");
        if (produs == null) {
            throw new NoSuchElementException("Nu se poate actualiza produsul!");
        }
        return produs;
    }

    /**
     * Sterge un produs din baza de date.
     *
     * @param p Produsul de șters.
     * @param id ID-ul produsului de șters.
     * @return Produsul șters.
     * @throws NoSuchElementException dacă nu se poate șterge produsul.
     */
    public Produs deleteProdus(Produs p, int id){
        Produs produs=produsDAO.delete(p,id,"idProdus");
        if (produs == null) {
            throw new NoSuchElementException("Nu se poate sterge produsul!");
        }
        return produs;
    }
}
