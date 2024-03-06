package model;

public class Produs {
    private int idProdus;
    private String numeProdus;
    private float pretProdus;
    private int cantitateProdus;

    /**
     * Constructorul cu parametri pentru obiectul Produs.
     *
     * @param idProdus         ID-ul produsului
     * @param numeProdus     numele produsului
     * @param pretProdus     pretul produsului
     * @param cantitateProdus  Cantitatea produsului
     */
    public Produs(int idProdus, String numeProdus, float pretProdus, int cantitateProdus) {
        this.idProdus = idProdus;
        this.numeProdus = numeProdus;
        this.pretProdus = pretProdus;
        this.cantitateProdus = cantitateProdus;
    }

    /**
     * Constructorul cu parametri pentru obiectul Produs.
     *
     * @param numeProdus     numele produsului
     * @param pretProdus     pretul produsului
     * @param cantitateProdus  Cantitatea produsului
     */
    public Produs(String numeProdus, float pretProdus, int cantitateProdus) {
        this.numeProdus = numeProdus;
        this.pretProdus = pretProdus;
        this.cantitateProdus = cantitateProdus;
    }

    /**
     * Constructorul fără parametri pentru obiectul Produs.
     */
    public Produs() {

    }

    public int getIdProdus() {
        return idProdus;
    }

    public void setIdProdus(int idProdus) {
        this.idProdus = idProdus;
    }

    public String getNumeProdus() {
        return numeProdus;
    }

    public void setNumeProdus(String numeProdus) {
        this.numeProdus = numeProdus;
    }

    public float getPretProdus() {
        return pretProdus;
    }

    public void setPretProdus(float pretProdus) {
        this.pretProdus = pretProdus;
    }

    public int getCantitateProdus() {
        return cantitateProdus;
    }

    public void setCantitateProdus(int cantitateProdus) {
        this.cantitateProdus = cantitateProdus;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "idProdus=" + idProdus +
                ", numeProdus='" + numeProdus + '\'' +
                ", pretProdus=" + pretProdus +
                ", cantitateProdus=" + cantitateProdus +
                '}';
    }
}
