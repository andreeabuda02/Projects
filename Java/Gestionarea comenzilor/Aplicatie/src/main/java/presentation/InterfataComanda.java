package presentation;

import bll.ClientBLL;
import bll.ComandaBLL;
import bll.FacturaBLL;
import bll.ProdusBLL;
import model.Client;
import model.Comanda;
import model.Factura;
import model.Produs;
import start.Reflection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class InterfataComanda extends JFrame {

    private ClientBLL clientBLL = new ClientBLL();
    private Reflection<Client> refC = new Reflection<>();
    private ProdusBLL produsBLL = new ProdusBLL();
    private Reflection<Produs> refP = new Reflection<>();
    private FacturaBLL facturaBLL = new FacturaBLL();

    //label-uri
    private ComandaBLL comandaBLL = new ComandaBLL();
    private Reflection<Comanda> ref = new Reflection<>();
    private JLabel titluText = new JLabel("Managementul comenzilor");

    private JLabel cText = new JLabel("Client:");
    private JLabel pText = new JLabel("Produs:");
    private JLabel idClientText = new JLabel("Id:");
    private JLabel numeCLientText = new JLabel("Nume:");
    private JLabel telefonClientText = new JLabel("Telefon:");
    private JLabel adresaClientText = new JLabel("Adresa:");
    private JLabel varstaClientText = new JLabel("Varsta:");

    private JLabel idProdusText = new JLabel("Id:");
    private JLabel numeProdusText = new JLabel("Nume:");
    private JLabel pretProdusText = new JLabel("Pret:");
    private JLabel cantitateProdusText = new JLabel("Cantitate:");
    private JLabel cantitateComandaText = new JLabel("Cantitatea dorita:");

    //casutele pentru text
    private JComboBox<String> idClientPanel = new JComboBox<>();
    private JTextField numeClientPanel = new JTextField();
    private JTextField telefonClientPanel = new JTextField();
    private JTextField adresaClientPanel = new JTextField();
    private JTextField varstaClientPanel = new JTextField();

    private JComboBox<String> idProdusPanel = new JComboBox<>();
    private JTextField numeProdusPanel = new JTextField();
    private JTextField pretProdusPanel = new JTextField();
    private JTextField cantitateProdusPanel = new JTextField();
    //mijloc
    private JTextField cantitateComandaPanel = new JTextField();


    //butoane
    private JButton executa = new JButton("EXECUTE");
    private JButton inchide = new JButton("CLOSE");
    private JButton inapoiMeniu = new JButton("BACK TO MENU");


    private JTable comandaTable;
    private DefaultTableModel comandaTableModel;

    private JScrollPane comandaScrollPane;

    private enum OperationType {
        INSERT, UPDATE, DELETE, NONE
    }

    private OperationType currentOperation = OperationType.NONE;


    /**
     * Această metodă configurează aspectul interfeței grafice.
     */
    public void aspectInterfata() {

        titluText.setBounds(250, 20, 440, 50);
        titluText.setBorder(BorderFactory.createLineBorder(new Color(184, 143, 239), 2));
        titluText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 30));

        cText.setBounds(100, 100, 80, 20);
        cText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 12));
        idClientText.setBounds(20, 130, 80, 20);
        idClientText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 12));
        numeCLientText.setBounds(20, 160, 80, 20);
        numeCLientText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 12));
        telefonClientText.setBounds(20, 190, 80, 20);
        telefonClientText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 12));
        adresaClientText.setBounds(20, 220, 80, 20);
        adresaClientText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 12));
        varstaClientText.setBounds(20, 250, 80, 20);
        varstaClientText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 12));


        idClientPanel.setBounds(80, 130, 150, 25);
        numeClientPanel.setBounds(80, 160, 150, 25);
        telefonClientPanel.setBounds(80, 190, 150, 25);
        adresaClientPanel.setBounds(80, 220, 150, 25);
        varstaClientPanel.setBounds(80, 250, 150, 25);

        pText.setBounds(820, 120, 80, 20);
        pText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 12));
        idProdusText.setBounds(735, 150, 80, 20);
        idProdusText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 12));
        numeProdusText.setBounds(735, 180, 80, 20);
        numeProdusText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 12));
        pretProdusText.setBounds(735, 210, 80, 20);
        pretProdusText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 12));
        cantitateProdusText.setBounds(735, 240, 80, 20);
        cantitateProdusText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 12));

        idProdusPanel.setBounds(800, 150, 150, 25);
        numeProdusPanel.setBounds(800, 180, 150, 25);
        pretProdusPanel.setBounds(800, 210, 150, 25);
        cantitateProdusPanel.setBounds(800, 240, 150, 25);

        cantitateComandaText.setBounds(330, 185, 120, 30);
        cantitateComandaText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 12));
        cantitateComandaPanel.setBounds(450, 185, 150, 35);

        //BUTOANE
        executa.setBounds(160, 645, 200, 40);
        inchide.setBounds(600, 645, 250, 40);
        inapoiMeniu.setBounds(380, 640, 200, 50);

        executa.setBackground(new Color(184, 143, 239));
        inchide.setBackground(new Color(184, 143, 239));
        inapoiMeniu.setBackground(new Color(184, 143, 239));

        executa.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
        inchide.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
        inapoiMeniu.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));


        comandaTableModel = new DefaultTableModel();
        comandaTableModel.addColumn("idComanda");
        comandaTableModel.addColumn("idClient");
        comandaTableModel.addColumn("idProdus");
        comandaTableModel.addColumn("Cantitate");

        comandaTable = new JTable(comandaTableModel);
        comandaTable.setPreferredScrollableViewportSize(new Dimension(800, 300));

        comandaTable = new JTable(comandaTableModel);
        comandaTable.setPreferredScrollableViewportSize(new Dimension(800, 300));

        comandaScrollPane = new JScrollPane(comandaTable);
        comandaScrollPane.setBounds(50, 280, 900, 350);
        add(comandaScrollPane);

    }

    /**
     * Această metodă adaugă componentele în interfața grafică.
     */
    private void adaugaComponente() {
        add(titluText);
        add(executa);
        add(inchide);
        add(inapoiMeniu);
        add(idClientPanel);
        add(numeClientPanel);
        add(adresaClientPanel);
        add(telefonClientPanel);
        add(idClientText);
        add(numeCLientText);
        add(adresaClientText);
        add(telefonClientText);
        add(varstaClientPanel);
        add(varstaClientText);
        add(cantitateComandaText);
        add(cantitateComandaPanel);
        add(cText);
        add(pText);
        add(idProdusPanel);
        add(numeProdusPanel);
        add(pretProdusPanel);
        add(cantitateProdusPanel);
        add(idProdusText);
        add(numeProdusText);
        add(pretProdusText);
        add(cantitateProdusText);
    }

    /**
     * Constructorul clasei `InterfataComanda`.
     * @param title Titlul ferestrei.
     * @throws HeadlessException Excepție aruncată în cazul în care nu există suport pentru interfețele grafice.
     */
    public InterfataComanda(String title) throws HeadlessException {
        //initializare frame
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        //butoane in frame
        aspectInterfata();
        adaugaComponente();
        //adauga panel in frame si pozitionarea la mijlocul ecranului si afisarea lui
        ImageIcon img = new ImageIcon("Purple.jpg");
        JPanel fereastraP = new PanelFereastra();
        fereastraP.setPreferredSize(new Dimension(1000, 700));
        add(fereastraP);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        getContentPane().setBackground(Color.PINK);

        List<Client> listaClienti = clientBLL.findAllClients();
        refC.idTabele(idClientPanel, listaClienti);
        List<Produs> listaProduse = produsBLL.findAllProducts();
        refP.idTabele(idProdusPanel, listaProduse);

        idClientPanel.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (idClientPanel.getItemCount() > 0) {
                    String string = String.valueOf(idClientPanel.getSelectedItem());
                    if (string.equals("Select id:")) {
                        numeClientPanel.setText("");
                        adresaClientPanel.setText("");
                        telefonClientPanel.setText("");
                        varstaClientPanel.setText("");
                    } else {
                        Client client = clientBLL.findClientById(Integer.parseInt(string));
                        numeClientPanel.setText(client.getNumeClient());
                        adresaClientPanel.setText(client.getAdresaClient());
                        telefonClientPanel.setText(client.getTelefonClient());
                        varstaClientPanel.setText(String.valueOf(client.getVarstaClient()));
                    }
                }
            }
        });

        idProdusPanel.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (idProdusPanel.getItemCount() > 0) {
                    String string = String.valueOf(idProdusPanel.getSelectedItem());
                    if (string.equals("Select id:")) {
                        numeProdusPanel.setText("");
                        pretProdusPanel.setText("");
                        cantitateProdusPanel.setText("");
                    } else {
                        Produs produs = produsBLL.findProdusById(Integer.parseInt(string));
                        numeProdusPanel.setText(produs.getNumeProdus());
                        pretProdusPanel.setText(String.valueOf(produs.getPretProdus()));
                        cantitateProdusPanel.setText(String.valueOf(produs.getCantitateProdus()));
                    }
                }
            }
        });

        executa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!cantitateComandaPanel.getText().isEmpty() && !cantitateProdusPanel.getText().isEmpty()) {
                    int cantitateDorita = Integer.parseInt(cantitateComandaPanel.getText());
                    int cantitateDisponibila = Integer.parseInt(cantitateProdusPanel.getText());
                    if (cantitateDorita <= cantitateDisponibila) {
                        // cantitatea dorită este validă
                        int cantitateRamasa = cantitateDisponibila - cantitateDorita;
                        cantitateProdusPanel.setText(Integer.toString(cantitateRamasa));

                        int idC= Integer.parseInt(String.valueOf(idClientPanel.getSelectedItem()));
                        int idP= Integer.parseInt(String.valueOf(idProdusPanel.getSelectedItem()));
                        Comanda comanda = new Comanda(0, idC,idP, cantitateDorita);
                        comandaBLL.insertComanda(comanda);

                        int idCom=comandaBLL.maxIdComanda();
                        Factura factura=new Factura(0,idCom,numeClientPanel.getText(),telefonClientPanel.getText(),adresaClientPanel.getText(),numeProdusPanel.getText(),Float.parseFloat(pretProdusPanel.getText()),cantitateDorita);
                        facturaBLL.insertFactura(factura);

                        Produs produs=new Produs(idProdusPanel.getSelectedIndex(),numeProdusPanel.getText(),Float.parseFloat(pretProdusPanel.getText()),Integer.parseInt(cantitateProdusPanel.getText()));
                        produsBLL.updateProdus(produs, idProdusPanel.getSelectedIndex());

                        // resetez câmpurile de text
                        cantitateComandaPanel.setText("");
                        // adaug comanda în tabela comandaTable
                        List<Comanda> listaComenzi = comandaBLL.findAllOrders();
                        ref.completeazaTabel(comandaTable,listaComenzi);
                    } else {
                        // Cantitatea dorită este mai mare decât cantitatea disponibilă
                        JOptionPane.showMessageDialog(null, "Cantitatea dorită este mai mare decât cantitatea disponibilă.");
                    }
                }
                else{
                        JOptionPane.showMessageDialog(null, "SELECTATI!");
                }
            }
        });


        inchide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        inapoiMeniu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                InterfataMeniu meniu = new InterfataMeniu("Meniu");
            }
        });
    }
}
