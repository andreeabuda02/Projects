package presentation;

import bll.ClientBLL;
import model.Client;
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

public class InterfataClient  extends JFrame {

    private ClientBLL clientBLL=new ClientBLL();
    private Reflection<Client> ref=new Reflection<>();
    //label-uri
    private JLabel titluText = new JLabel("Managementul clientilor");

    private JLabel idClientText = new JLabel("Id:");
    private JLabel numeCLientText = new JLabel("Nume:");
    private JLabel telefonClientText = new JLabel("Telefon:");
    private JLabel adresaClientText = new JLabel("Adresa:");

    private JLabel varstaClientText = new JLabel("Varsta:");

    //casutele pentru text
    private JComboBox<String> idClientPanel = new JComboBox<>();
    private JTextField numeClientPanel = new JTextField();
    private JTextField telefonClientPanel = new JTextField();
    private JTextField adresaClientPanel = new JTextField();
    private JTextField varstaClientPanel = new JTextField();


    //butoane
    private JButton adaugaClient = new JButton("INSERT");
    private JButton stergeClient = new JButton("DELETE");
    private JButton updateClient = new JButton("UPDATE");
    private JButton afiseazaClienti = new JButton("SHOW ALL");
    private JButton executa = new JButton("EXECUTE");
    private JButton inchide = new JButton("CLOSE");
    private JButton inapoiMeniu = new JButton("BACK TO MENU");


    private JTable clientTable;
    private DefaultTableModel clientTableModel;

    private JScrollPane clientScrollPane;

    private enum OperationType {
        INSERT, UPDATE, DELETE, NONE
    }

    private OperationType currentOperation = OperationType.NONE;


    /**
     * Această metodă setează aspectul interfeței utilizator pentru fereastra de gestionare a clienților.
     */
    public void aspectInterfata() {

        titluText.setBounds(250, 30, 440, 50);
        titluText.setBorder(BorderFactory.createLineBorder(new Color(184, 143, 239), 2));
        titluText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 30));

        idClientText.setBounds(50, 190, 80, 30);
        //idClientText.setBorder(BorderFactory.createLineBorder(new Color(184, 143, 239), 2));
        idClientText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 15));
        numeCLientText.setBounds(50, 270, 80, 30);
        //numeCLientText.setBorder(BorderFactory.createLineBorder(new Color(184, 143, 239), 2));
        numeCLientText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 15));
        telefonClientText.setBounds(50, 350, 80, 30);
        //telefonClientText.setBorder(BorderFactory.createLineBorder(new Color(184, 143, 239), 2));
        telefonClientText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 15));
        adresaClientText.setBounds(50, 430, 80, 30);
        //adresaClientText.setBorder(BorderFactory.createLineBorder(new Color(184, 143, 239), 2));
        adresaClientText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 15));
        varstaClientText.setBounds(50, 510, 80, 30);
        varstaClientText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 15));

        idClientPanel.setBounds(135, 190, 150, 35);
        numeClientPanel.setBounds(135, 270, 150, 35);
        telefonClientPanel.setBounds(135, 350, 150, 35);
        adresaClientPanel.setBounds(135, 430, 150, 35);
        varstaClientPanel.setBounds(135, 510, 150, 35);

        adaugaClient.setBounds(50, 120, 200, 40);
        stergeClient.setBounds(280, 120, 200, 40);
        updateClient.setBounds(510, 120, 200, 40);
        afiseazaClienti.setBounds(740, 120, 200, 40);
        executa.setBounds(250, 570, 200, 40);
        inchide.setBounds(500, 570, 250, 40);
        inapoiMeniu.setBounds(380, 620, 200, 50);

        adaugaClient.setBackground(new Color(184, 143, 239));
        stergeClient.setBackground(new Color(184, 143, 239));
        updateClient.setBackground(new Color(184, 143, 239));
        afiseazaClienti.setBackground(new Color(184, 143, 239));
        executa.setBackground(new Color(184, 143, 239));
        inchide.setBackground(new Color(184, 143, 239));
        inapoiMeniu.setBackground(new Color(184, 143, 239));

        adaugaClient.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
        stergeClient.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
        updateClient.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
        afiseazaClienti.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
        executa.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
        inchide.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
        inapoiMeniu.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));


        clientTableModel = new DefaultTableModel();
        clientTableModel.addColumn("idClient");
        clientTableModel.addColumn("numeClient");
        clientTableModel.addColumn("telefonClient");
        clientTableModel.addColumn("adresaClient");

        clientTable = new JTable(clientTableModel);
        clientTable.setPreferredScrollableViewportSize(new Dimension(800, 300));

        clientTable = new JTable(clientTableModel);
        clientTable.setPreferredScrollableViewportSize(new Dimension(800, 300));

        clientScrollPane = new JScrollPane(clientTable);
        clientScrollPane.setBounds(300, 190, 650, 330);
        add(clientScrollPane);


    }

    /**
     * Această metodă adaugă componente în fereastra de gestionare a clienților.
     */
    private void adaugaComponente() {
        add(titluText);
        add(adaugaClient);
        add(stergeClient);
        add(updateClient);
        add(afiseazaClienti);
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
    }

    /**
     * Această metodă ascunde elementele care nu sunt necesare în funcție de operația curentă.
     */
    private void ascundeElemente(){
        idClientText.setVisible(false);
        numeCLientText.setVisible(false);
        telefonClientText.setVisible(false);
        adresaClientText.setVisible(false);
        idClientPanel.setVisible(false);
        numeClientPanel.setVisible(false);
        telefonClientPanel.setVisible(false);
        adresaClientPanel.setVisible(false);
        clientScrollPane.setVisible(false);
        varstaClientPanel.setVisible(false);
        varstaClientText.setVisible(false);
    }

    /**
     * Această metodă afișează elementele necesare pentru operația de inserare a unui client.
     */
    private void afiseazaPtInsert(){
        numeCLientText.setVisible(true);
        telefonClientText.setVisible(true);
        adresaClientText.setVisible(true);
        numeClientPanel.setVisible(true);
        telefonClientPanel.setVisible(true);
        adresaClientPanel.setVisible(true);
        varstaClientPanel.setVisible(true);
        varstaClientText.setVisible(true);
    }

    /**
     * Această metodă afișează elementele necesare pentru operația de actualizare a unui client.
     */
    private void afiseazaPtUpdate(){
        idClientText.setVisible(true);
        numeCLientText.setVisible(true);
        telefonClientText.setVisible(true);
        adresaClientText.setVisible(true);
        idClientPanel.setVisible(true);
        numeClientPanel.setVisible(true);
        telefonClientPanel.setVisible(true);
        adresaClientPanel.setVisible(true);
        varstaClientPanel.setVisible(true);
        varstaClientText.setVisible(true);
    }


    /**
     * Această metodă afișează elementele necesare pentru operația de ștergere a unui client.
     */
    private void afiseazaPtDelete(){
        idClientText.setVisible(true);
        idClientPanel.setVisible(true);
    }

    /**
     * Această metodă inițializează fereastra de interfață utilizator pentru gestionarea clienților.
     *
     * @param title Titlul ferestrei.
     * @throws HeadlessException Excepție aruncată în cazul în care nu există suport pentru interfața grafică.
     */
    public InterfataClient(String title) throws HeadlessException {
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

        ascundeElemente();

        idClientPanel.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(idClientPanel.getItemCount()>0){
                    String string=String.valueOf(idClientPanel.getSelectedItem());
                    if(string.equals("Select id:")){
                        numeClientPanel.setText("");
                        adresaClientPanel.setText("");
                        telefonClientPanel.setText("");
                        varstaClientPanel.setText("");
                    }
                    else{
                        Client client=clientBLL.findClientById(Integer.parseInt(string));
                        numeClientPanel.setText(client.getNumeClient());
                        adresaClientPanel.setText(client.getAdresaClient());
                        telefonClientPanel.setText(client.getTelefonClient());
                        varstaClientPanel.setText(String.valueOf(client.getVarstaClient()));
                    }
                }
            }
        });

        //actiune facuta de butonul de iesire e aceeasi ca a butonului X

        adaugaClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentOperation = OperationType.INSERT;
                ascundeElemente();
                afiseazaPtInsert();
            }
        });

        updateClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentOperation = OperationType.UPDATE;
                ascundeElemente();
                afiseazaPtUpdate();
                List<Client> listaClienti = clientBLL.findAllClients();
                ref.idTabele(idClientPanel, listaClienti);
            }
        });

        stergeClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentOperation = OperationType.DELETE;
                ascundeElemente();
                afiseazaPtDelete();
                List<Client> listaClienti = clientBLL.findAllClients();
                ref.idTabele(idClientPanel, listaClienti);
            }
        });

        afiseazaClienti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentOperation = OperationType.NONE;
                ascundeElemente();
                clientScrollPane.setVisible(true);
                List<Client> listaClienti = clientBLL.findAllClients();
                ref.completeazaTabel(clientTable,listaClienti);
            }
        });


        executa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentOperation == OperationType.INSERT) {
                    // execut operatia de inserare
                    // obțin valorile din câmpurile de text
                    String nume = numeClientPanel.getText();
                    String telefon = telefonClientPanel.getText();
                    String adresa = adresaClientPanel.getText();
                    int varsta;

                    // verific dacă câmpurile sunt completate
                    if (nume.isEmpty() || telefon.isEmpty() || adresa.isEmpty() || varstaClientPanel.getText().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Completați toate câmpurile pentru a adăuga un client.");
                        return;
                    }

                    try {
                        varsta =Integer.parseInt(varstaClientPanel.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Varsta poate contine doar cifre!");
                        return;
                    }

                    if(telefon.length()!=10){
                        JOptionPane.showMessageDialog(null,"Numarul de telefon trebuie sa aiba 10 cifre!");
                        return;
                    }

                    Client client = new Client(0,nume,varsta,adresa,telefon);
                    clientBLL.insertClient(client);

                    // resetez câmpurile de text
                    numeClientPanel.setText("");
                    telefonClientPanel.setText("");
                    adresaClientPanel.setText("");
                    varstaClientPanel.setText("");
                } else if (currentOperation == OperationType.UPDATE) {
                    // execut operatia de actualizare


                    if (idClientPanel.getSelectedIndex() != 0) {
                        // obtin valorile din câmpurile de text
                        int id;
                        String nume = numeClientPanel.getText();
                        String telefon = telefonClientPanel.getText();
                        String adresa = adresaClientPanel.getText();
                        int varsta;
                        // verific dacă câmpurile sunt completate
                        if (nume.isEmpty() || telefon.isEmpty() || adresa.isEmpty() || varstaClientPanel.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Completați toate câmpurile pentru a actualiza un client.");
                            return;
                        }

                        try {
                            id = Integer.parseInt(String.valueOf(idClientPanel.getSelectedItem()));
                            varsta =Integer.parseInt(varstaClientPanel.getText());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Varsta poate contine doar cifre!");
                            return;
                        }

                        if(telefon.length()!=10){
                            JOptionPane.showMessageDialog(null,"Numarul de telefon trebuie sa aiba 10 cifre!");
                            return;
                        }

                        Client client = new Client(id,nume,varsta,adresa,telefon);
                        clientBLL.updateClient(client, id);

                        // resetez câmpurile de text
                        numeClientPanel.setText("");
                        telefonClientPanel.setText("");
                        adresaClientPanel.setText("");
                        varstaClientPanel.setText("");
                        idClientPanel.setSelectedIndex(0);

                    } else {
                        JOptionPane.showMessageDialog(null, "Selectați un client din tabel pentru a-l actualiza.");
                    }
                } else if (currentOperation == OperationType.DELETE) {
                    // execut operatia de stergere
                    if (idClientPanel.getSelectedIndex() != 0) {
                        int id = Integer.parseInt(String.valueOf(idClientPanel.getSelectedItem()));
                        Client client=new Client();
                        clientBLL.deleteClient(client,id);
                    } else {
                        JOptionPane.showMessageDialog(null, "Selectați un client din tabel pentru a-l șterge.");
                    }
                }

                // resetez variabila currentOperation la NONE
                currentOperation = OperationType.NONE;
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
