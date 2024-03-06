package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class InterfataMeniu extends JFrame{

    private JLabel titluText = new JLabel("Managementul comenzilor");

    //butoane
    private JButton clientiGestiune = new JButton("Gestionare clienti");
    private JButton produseGestiune = new JButton("Gestionare produse");
    private JButton comandaGestiune = new JButton("Creare comanda");
    private JButton iesire = new JButton("EXIT");

    private InterfataMeniu frame;

    /**
     * Metoda pentru configurarea aspectului interfeței grafice.
     */
    public void aspectInterfata() {

        titluText.setBounds(250, 30, 440, 60);
        titluText.setBorder(BorderFactory.createLineBorder(new Color(184, 143, 239), 2));
        titluText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 30));

        clientiGestiune.setBounds(360, 170, 250, 70);
        produseGestiune.setBounds(360, 290, 250, 70);
        comandaGestiune.setBounds(360, 410, 250, 70);
        iesire.setBounds(380, 570, 200, 45);

        clientiGestiune.setBackground(new Color(184, 143, 239));
        produseGestiune.setBackground(new Color(184, 143, 239));
        comandaGestiune.setBackground(new Color(184, 143, 239));
        iesire.setBackground(new Color(184, 143, 239));

        clientiGestiune.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
        produseGestiune.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
        comandaGestiune.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
        iesire.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
    }

    /**
     * Metoda pentru adăugarea componentelor în interfață.
     */
    private void adaugaComponente() {
        add(titluText);
        add(clientiGestiune);
        add(comandaGestiune);
        add(produseGestiune);
        add(iesire);
    }

    /**
     * Constructorul clasei InterfataMeniu.
     * @param title Titlul ferestrei.
     */
    public InterfataMeniu(String title) throws HeadlessException {
        //initializare frame
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        frame = this;
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

        //actiune facuta de butonul de iesire e aceeasi ca a butonului X
        iesire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        comandaGestiune.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                InterfataComanda interfataComanda = new InterfataComanda("Gestionare comenzi");
                interfataComanda.setVisible(true);
            }
        });

        clientiGestiune.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                InterfataClient interfataClient = new InterfataClient("Gestionare clienți");
                interfataClient.setVisible(true);
            }
        });



        produseGestiune.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                InterfataProdus interfataProdus = new InterfataProdus("Gestionare produse");
                interfataProdus.setVisible(true);
            }
        });
    }

}
