package model;

/**
 * Aceasta este clasa care reprezintă un client.
 */
public class Client {
    private int idClient;
    private String numeClient;
    private int varstaClient;
    private String adresaClient;
    private String telefonClient;

    /**
     * Constructorul cu parametri pentru obiectul Client.
     *
     * @param idClient       ID-ul clientului
     * @param numeClient     Numele clientului
     * @param varstaClient   Vârsta clientului
     * @param adresaClient   Adresa clientului
     * @param telefonClient  Numărul de telefon al clientului
     */
    public Client(int idClient, String numeClient, int varstaClient, String adresaClient, String telefonClient) {
        this.idClient = idClient;
        this.numeClient = numeClient;
        this.varstaClient = varstaClient;
        this.adresaClient = adresaClient;
        this.telefonClient = telefonClient;
    }

    /**
     * Constructorul cu parametri pentru obiectul Client.
     *
     * @param numeClient     Numele clientului
     * @param varstaClient   Vârsta clientului
     * @param adresaClient   Adresa clientului
     * @param telefonClient  Numărul de telefon al clientului
     */
    public Client(String numeClient, int varstaClient, String adresaClient, String telefonClient) {
        this.numeClient = numeClient;
        this.varstaClient = varstaClient;
        this.adresaClient = adresaClient;
        this.telefonClient = telefonClient;
    }


    /**
     * Constructorul fără parametri pentru obiectul Client.
     */
    public Client() {

    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getNumeClient() {
        return numeClient;
    }

    public void setNumeClient(String numeClient) {
        this.numeClient = numeClient;
    }

    public int getVarstaClient() {
        return varstaClient;
    }

    public void setVarstaClient(int varstaClient) {
        this.varstaClient = varstaClient;
    }

    public String getAdresaClient() {
        return adresaClient;
    }

    public void setAdresaClient(String adresaClient) {
        this.adresaClient = adresaClient;
    }

    public String getTelefonClient() {
        return telefonClient;
    }

    public void setTelefonClient(String telefonClient) {
        this.telefonClient = telefonClient;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", numeClient='" + numeClient + '\'' +
                ", varstaClient=" + varstaClient +
                ", adresaClient='" + adresaClient + '\'' +
                ", telefonClient='" + telefonClient + '\'' +
                '}';
    }
}
