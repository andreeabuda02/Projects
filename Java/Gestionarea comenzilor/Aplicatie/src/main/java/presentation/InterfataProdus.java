package presentation;

import bll.ProdusBLL;
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

public class InterfataProdus  extends JFrame {

    private ProdusBLL produsBLL=new ProdusBLL();
    private Reflection<Produs> ref=new Reflection<>();

    //label-uri
    private JLabel titluText = new JLabel("Managementul produselor");

    private JLabel idProdusText = new JLabel("Id:");
    private JLabel numeProdusText = new JLabel("Nume:");
    private JLabel pretProdusText = new JLabel("Pret:");
    private JLabel cantitateProdusText = new JLabel("Cantitate:");

    //casutele pentru text
    private JComboBox<String> idProdusPanel = new JComboBox<>();
    private JTextField numeProdusPanel = new JTextField();
    private JTextField pretProdusPanel = new JTextField();
    private JTextField cantitateProdusPanel = new JTextField();


    //butoane
    private JButton adaugaProdus = new JButton("INSERT");
    private JButton stergeProdus = new JButton("DELETE");
    private JButton updateProdus = new JButton("UPDATE");
    private JButton afiseazaProdus = new JButton("SHOW ALL");
    private JButton executa = new JButton("EXECUTE");
    private JButton inchide = new JButton("CLOSE");
    private JButton inapoiMeniu = new JButton("BACK TO MENU");


    private JTable produsTable;
    private DefaultTableModel produsTableModel;

    private JScrollPane produsScrollPane;

    private enum OperationType {
        INSERT, UPDATE, DELETE, NONE
    }

    private OperationType currentOperation = OperationType.NONE;


    /**
     * Această metodă setează aspectul interfeței utilizator pentru fereastra de gestionare a produselor.
     */
    public void aspectInterfata() {

        titluText.setBounds(250, 30, 440, 50);
        titluText.setBorder(BorderFactory.createLineBorder(new Color(184, 143, 239), 2));
        titluText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 30));

        idProdusText.setBounds(50, 190, 80, 30);
        //idClientText.setBorder(BorderFactory.createLineBorder(new Color(184, 143, 239), 2));
        idProdusText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 15));
        numeProdusText.setBounds(50, 290, 80, 30);
        //numeCLientText.setBorder(BorderFactory.createLineBorder(new Color(184, 143, 239), 2));
        numeProdusText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 15));
        pretProdusText.setBounds(50, 390, 80, 30);
        //telefonClientText.setBorder(BorderFactory.createLineBorder(new Color(184, 143, 239), 2));
        pretProdusText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 15));
        cantitateProdusText.setBounds(50, 490, 80, 30);
        //adresaClientText.setBorder(BorderFactory.createLineBorder(new Color(184, 143, 239), 2));
        cantitateProdusText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 15));

        idProdusPanel.setBounds(135, 190, 150, 35);
        numeProdusPanel.setBounds(135, 290, 150, 35);
        pretProdusPanel.setBounds(135, 390, 150, 35);
        cantitateProdusPanel.setBounds(135, 490, 150, 35);

        adaugaProdus.setBounds(50, 120, 200, 40);
        stergeProdus.setBounds(280, 120, 200, 40);
        updateProdus.setBounds(510, 120, 200, 40);
        afiseazaProdus.setBounds(740, 120, 200, 40);
        executa.setBounds(250, 570, 200, 40);
        inchide.setBounds(500, 570, 250, 40);
        inapoiMeniu.setBounds(380, 620, 200, 50);

        adaugaProdus.setBackground(new Color(184, 143, 239));
        stergeProdus.setBackground(new Color(184, 143, 239));
        updateProdus.setBackground(new Color(184, 143, 239));
        afiseazaProdus.setBackground(new Color(184, 143, 239));
        executa.setBackground(new Color(184, 143, 239));
        inchide.setBackground(new Color(184, 143, 239));
        inapoiMeniu.setBackground(new Color(184, 143, 239));

        adaugaProdus.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
        stergeProdus.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
        updateProdus.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
        afiseazaProdus.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
        executa.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
        inchide.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
        inapoiMeniu.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));


        produsTableModel = new DefaultTableModel();
        produsTableModel.addColumn("idProdus");
        produsTableModel.addColumn("numeProdus");
        produsTableModel.addColumn("pretProdus");
        produsTableModel.addColumn("cantitateProdus");

        produsTable = new JTable(produsTableModel);
        produsTable.setPreferredScrollableViewportSize(new Dimension(800, 300));

        produsTable = new JTable(produsTableModel);
        produsTable.setPreferredScrollableViewportSize(new Dimension(800, 300));

        produsScrollPane = new JScrollPane(produsTable);
        produsScrollPane.setBounds(300, 190, 650, 330);
        add(produsScrollPane);


    }

    /**
     * Această metodă adaugă componente în fereastra de gestionare a produselor.
     */
    private void adaugaComponente() {
        add(titluText);
        add(adaugaProdus);
        add(stergeProdus);
        add(updateProdus);
        add(afiseazaProdus);
        add(executa);
        add(inchide);
        add(inapoiMeniu);
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
     * Această metodă ascunde elementele care nu sunt necesare în funcție de operația curentă.
     */
    private void ascundeElemente(){
        idProdusPanel.setVisible(false);
        idProdusText.setVisible(false);
        numeProdusPanel.setVisible(false);
        numeProdusText.setVisible(false);
        pretProdusPanel.setVisible(false);
        pretProdusText.setVisible(false);
        cantitateProdusPanel.setVisible(false);
        cantitateProdusText.setVisible(false);
        produsScrollPane.setVisible(false);
    }

    /**
     * Această metodă afișează elementele necesare pentru operația de inserare a unui produs.
     */
    private void afiseazaPtInsert(){
        numeProdusPanel.setVisible(true);
        numeProdusText.setVisible(true);
        pretProdusPanel.setVisible(true);
        pretProdusText.setVisible(true);
        cantitateProdusPanel.setVisible(true);
        cantitateProdusText.setVisible(true);
    }

    /**
     * Această metodă afișează elementele necesare pentru operația de actualizare a unui produs.
     */
    private void afiseazaPtUpdate(){
        idProdusPanel.setVisible(true);
        idProdusText.setVisible(true);
        numeProdusPanel.setVisible(true);
        numeProdusText.setVisible(true);
        pretProdusPanel.setVisible(true);
        pretProdusText.setVisible(true);
        cantitateProdusPanel.setVisible(true);
        cantitateProdusText.setVisible(true);
    }

    /**
     * Această metodă afișează elementele necesare pentru operația de ștergere a unui produs.
     */
    private void afiseazaPtDelete(){
        idProdusPanel.setVisible(true);
        idProdusText.setVisible(true);
    }

    /**
     * Această metodă inițializează fereastra de interfață utilizator pentru gestionarea produselor.
     *
     * @param title Titlul ferestrei.
     * @throws HeadlessException Excepție aruncată în cazul în care nu există suport pentru interfața grafică.
     */
    public InterfataProdus(String title) throws HeadlessException {
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

        idProdusPanel.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(idProdusPanel.getItemCount()>0){
                    String string=String.valueOf(idProdusPanel.getSelectedItem());
                    if(string.equals("Select id:")){
                        numeProdusPanel.setText("");
                        pretProdusPanel.setText("");
                        cantitateProdusPanel.setText("");
                    }
                    else{
                        Produs produs=produsBLL.findProdusById(Integer.parseInt(string));
                        numeProdusPanel.setText(produs.getNumeProdus());
                        pretProdusPanel.setText(String.valueOf(produs.getPretProdus()));
                        cantitateProdusPanel.setText(String.valueOf(produs.getCantitateProdus()));
                    }
                }
            }
        });

        adaugaProdus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentOperation = OperationType.INSERT;
                ascundeElemente();
                afiseazaPtInsert();
            }
        });

        updateProdus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentOperation = OperationType.UPDATE;
                ascundeElemente();
                afiseazaPtUpdate();
                List<Produs> listaProduse=produsBLL.findAllProducts();
                ref.idTabele(idProdusPanel,listaProduse);
            }
        });

        stergeProdus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentOperation = OperationType.DELETE;
                ascundeElemente();
                afiseazaPtDelete();
                List<Produs> listaProduse=produsBLL.findAllProducts();
                ref.idTabele(idProdusPanel,listaProduse);
            }
        });

        afiseazaProdus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentOperation=OperationType.NONE;
                ascundeElemente();
                produsScrollPane.setVisible(true);
                List<Produs> listaProduse=produsBLL.findAllProducts();
                ref.completeazaTabel(produsTable,listaProduse);
            }
        });


        executa.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentOperation == OperationType.INSERT) {
                    // execut operatia de inserare
                    String nume = numeProdusPanel.getText();
                    float pret;
                    int cantitate;

                    //verific daca campurile sunt completate
                    if(nume.isEmpty()||pretProdusPanel.getText().isEmpty()||cantitateProdusPanel.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null, "Completați toate câmpurile pentru a adăuga un produs.");
                        return;
                    }

                    try {
                        pret =Float.parseFloat(pretProdusPanel.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Pretul poate contine doar cifre!");
                        return;
                    }

                    try {
                        cantitate =Integer.parseInt(cantitateProdusPanel.getText());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Cantitatea poate contine doar cifre!");
                        return;
                    }

                    Produs produs=new Produs(0,nume,pret,cantitate);
                    produsBLL.insertProdus(produs);;

                    // resetez câmpurile de text
                    numeProdusPanel.setText("");
                    pretProdusPanel.setText("");
                    cantitateProdusPanel.setText("");

                } else if (currentOperation == OperationType.UPDATE) {
                    // execut operatia de actualizare
                    if(idProdusPanel.getSelectedIndex()!=0){
                        int id;
                        String nume = numeProdusPanel.getText();
                        float pret;
                        int cantitate;
                        //verific daca campurile sunt completate
                        if(nume.isEmpty()||pretProdusPanel.getText().isEmpty()||cantitateProdusPanel.getText().isEmpty()){
                            JOptionPane.showMessageDialog(null, "Completați toate câmpurile pentru a adăuga un produs.");
                            return;
                        }

                        try {
                            pret =Integer.parseInt(pretProdusPanel.getText());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Pretul poate contine doar cifre!");
                            return;
                        }

                        try {
                            cantitate =Integer.parseInt(cantitateProdusPanel.getText());
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Cantitatea poate contine doar cifre!");
                            return;
                        }

                        try {
                            id = Integer.parseInt(String.valueOf(idProdusPanel.getSelectedItem()));
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Selecteaza un id!");
                            return;
                        }

                        Produs produs=new Produs(id,nume,pret,cantitate);
                        produsBLL.updateProdus(produs, id);;

                        // resetez câmpurile de text
                        numeProdusPanel.setText("");
                        pretProdusPanel.setText("");
                        cantitateProdusPanel.setText("");
                        idProdusPanel.setSelectedIndex(0);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Selectați un produs din tabel pentru a-l actualiza.");
                    }
                } else if (currentOperation == OperationType.DELETE) {
                    // execut operatia de stergere
                    if(idProdusPanel.getSelectedIndex()!=0){
                        int id=Integer.parseInt(String.valueOf(idProdusPanel.getSelectedItem()));
                        Produs produs=new Produs();
                        produsBLL.deleteProdus(produs,id);
                    }else{
                        JOptionPane.showMessageDialog(null, "Selectați un produs din tabel pentru a-l șterge.");
                    }
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
