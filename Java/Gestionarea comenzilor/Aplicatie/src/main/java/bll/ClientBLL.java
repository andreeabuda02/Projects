package bll;

import dao.ClientDAO;
import model.Client;

import java.util.List;
import java.util.NoSuchElementException;

public class ClientBLL {
    private ClientDAO clientDAO;

    /**
     * Creează o instanță a clasei ClientBLL și initializează obiectul de acces la date ClientDAO.
     */
    public ClientBLL() {
        clientDAO=new ClientDAO();
    }

    /**
     * Găsește un client după ID.
     *
     * @param id ID-ul clientului căutat.
     * @return Clientul găsit.
     * @throws NoSuchElementException dacă clientul nu a fost găsit în baza de date.
     */
    public Client findClientById(int id){
        Client client=clientDAO.findById(id, "idClient");
        if (client == null) {
            throw new NoSuchElementException("Clientul cu acest id ("+ id + ") nu s-a gasit!");
        }
        return client;
    }

    /**
     * Returnează o listă cu toți clienții existenți în baza de date.
     *
     * @return Lista de clienți.
     * @throws NoSuchElementException dacă nu există clienți în lista.
     */
    public List<Client> findAllClients(){
        List<Client> clienti=clientDAO.findAll();
        if (clienti == null) {
            throw new NoSuchElementException("Nu exista clienti in lista!");
        }
        return clienti;
    }


    /**
     * Inserează un client în baza de date.
     *
     * @param c Clientul de inserat.
     * @return Clientul inserat.
     * @throws NoSuchElementException dacă nu se poate insera clientul.
     */
    public Client insertClient(Client c){
        Client client=clientDAO.insert(c);
        if (client == null) {
            throw new NoSuchElementException("Nu se poate insera clientul!");
        }
        return client;
    }

    /**
     * Actualizează un client din baza de date.
     *
     * @param c Clientul actualizat.
     * @param id ID-ul clientului de actualizat.
     * @return Clientul actualizat.
     * @throws NoSuchElementException dacă nu se poate actualiza clientul.
     */
    public Client updateClient(Client c, int id){
        Client client=clientDAO.update(c,id,"idClient");
        if (client == null) {
            throw new NoSuchElementException("Nu se poate actualiza clientul!");
        }
        return client;
    }

    /**
     * Șterge un client din baza de date.
     *
     * @param c Clientul de șters.
     * @param id ID-ul clientului de șters.
     * @return Clientul șters.
     * @throws NoSuchElementException dacă nu se poate șterge clientul.
     */
    public Client deleteClient(Client c, int id){
        Client client=clientDAO.delete(c,id,"idClient");
        if (client == null) {
            throw new NoSuchElementException("Nu se poate sterge clientul!");
        }
        return client;
    }
}
