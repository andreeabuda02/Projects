package model;

/**
 * Aceasta este înregistrarea care reprezintă o factură.
 */
public record Factura(int idFactura,int comandaFactura,String numeClient,String telefonClient,String adresaClient,String numeProdus,float pretProdus,int cantitateProdus) {
}
