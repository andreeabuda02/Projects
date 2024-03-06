package start;

import presentation.InterfataMeniu;
import bll.ClientBLL;
import model.Client;
import presentation.InterfataClient;
import presentation.InterfataComanda;
import presentation.InterfataMeniu;
import presentation.InterfataProdus;

/**
 * Aceasta este clasa de start a aplicației.
 */

public class Start {
    public static void main( String[] args )
    {
        //Client client = new Client(0,"Andreea",21,"Tg Lapus","0787893376");
        //ClientBLL clientBLL = new ClientBLL();
        //clientBLL.insertClient(client);
        //client.setNumeClient("Ilinca");
        //clientBLL.updateClient(client, 1, "idClient");

        InterfataMeniu f=new InterfataMeniu("Meniu principal");
        //InterfataClient f=new InterfataClient("Meniu");
        //InterfataProdus f=new InterfataProdus("Meniu");
        //InterfataComanda f=new InterfataComanda("Meniu");

    }
}
