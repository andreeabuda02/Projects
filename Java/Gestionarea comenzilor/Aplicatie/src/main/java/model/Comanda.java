package model;

/**
 * Aceasta este clasa care reprezintă o comandă.
 */
public class Comanda {
    private int idComanda;
    private int clientComanda;
    private int produsComanda;
    private int cantitateComanda;

    /**
     * Constructorul cu parametri pentru obiectul Comanda.
     *
     * @param idComanda         ID-ul comenzii
     * @param clientComanda     ID-ul clientului asociat comenzii
     * @param produsComanda     ID-ul produsului asociat comenzii
     * @param cantitateComanda  Cantitatea comenzii
     */
    public Comanda(int idComanda, int clientComanda, int produsComanda, int cantitateComanda) {
        this.idComanda = idComanda;
        this.clientComanda = clientComanda;
        this.produsComanda = produsComanda;
        this.cantitateComanda = cantitateComanda;
    }

    /**
     * Constructorul cu parametri pentru obiectul Comanda.
     *
     * @param clientComanda     ID-ul clientului asociat comenzii
     * @param produsComanda     ID-ul produsului asociat comenzii
     * @param cantitateComanda  Cantitatea comenzii
     */
    public Comanda(int clientComanda, int produsComanda, int cantitateComanda) {
        this.clientComanda = clientComanda;
        this.produsComanda = produsComanda;
        this.cantitateComanda = cantitateComanda;
    }

    /**
     * Constructorul fără parametri pentru obiectul Comanda.
     */
    public Comanda() {

    }

    public int getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(int idComanda) {
        this.idComanda = idComanda;
    }

    public int getClientComanda() {
        return clientComanda;
    }

    public void setClientComanda(int clientComanda) {
        this.clientComanda = clientComanda;
    }

    public int getProdusComanda() {
        return produsComanda;
    }

    public void setProdusComanda(int produsComanda) {
        this.produsComanda = produsComanda;
    }

    public int getCantitateComanda() {
        return cantitateComanda;
    }

    public void setCantitateComanda(int cantitateComanda) {
        this.cantitateComanda = cantitateComanda;
    }

    @Override
    public String toString() {
        return "Comanda{" +
                "idComanda=" + idComanda +
                ", clientComanda=" + clientComanda +
                ", produsComanda=" + produsComanda +
                ", cantitateComanda=" + cantitateComanda +
                '}';
    }
}
