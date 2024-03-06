package clase.interfata;

import clase.exception.CasutaGoalaExceptie;
import clase.exception.DateGresiteExceptie;
import clase.logic.SimulationManager;
import clase.model.Server;
import clase.model.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SimulationFrame extends JFrame {
    //label-uri
    private JLabel cozileMagazinuluiText = new JLabel("Cozi la magazin");
    private JLabel timeLimitText = new JLabel("Limita de timp:");
    private JLabel minServiceTimeText = new JLabel("Timp minim servire:");
    private JLabel maxServiceTimeText = new JLabel("Timp maxim servire:");
    private JLabel minArrivalTimeText = new JLabel("Timp minim sosire:");
    private JLabel maxArrivalTimeText = new JLabel("Timp maxim sosire:");
    private JLabel numberOfClientsText = new JLabel("Numar de clienti:");
    private JLabel numberOfQueuesText = new JLabel("Numar de cozi:");
    private JLabel simulationText = new JLabel("Simularea:");

    //casutele pentru text
    private JTextField timeLimitPanel = new JTextField();
    private JTextField minServiceTimePanel = new JTextField();
    private JTextField maxServiceTimePanel = new JTextField();
    private JTextField minArrivalTimePanel = new JTextField();
    private JTextField maxArrivalTimePanel = new JTextField();
    private JTextField numberOfClientsPanel = new JTextField();
    private JTextField numberOfQueuesPanel = new JTextField();
    private JTextArea simulationPanel = new JTextArea();

    //butoane
    private JButton inceput = new JButton("START");
    private JButton oprire = new JButton("STOP");
    private JButton iesire = new JButton("EXIT");

    //JScrollPane scroll;
    private int timpLimita;
    private int timpMinServire;
    private int timpMinSosire;
    private int timpMaxServire;
    private int timpMaxSosire;
    private int nrClienti;
    private int nrCozi;

    private JScrollPane scroll = new JScrollPane(simulationPanel);

    private SimulationFrame frame;

    private FileWriter file;

    private boolean o=false;

    //date
    public void aspectInterfata() {

        cozileMagazinuluiText.setBounds(320, 30, 300, 50);
        cozileMagazinuluiText.setBorder(BorderFactory.createLineBorder(new Color(184, 143, 239), 2));
        cozileMagazinuluiText.setFont(new Font("Georgia", Font.BOLD | Font.ITALIC, 35));

        timeLimitText.setBounds(50, 150, 150, 40);
        timeLimitText.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 17));
        minServiceTimeText.setBounds(50, 210, 160, 40);
        minServiceTimeText.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 17));
        maxServiceTimeText.setBounds(50, 270, 165, 40);
        maxServiceTimeText.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 17));
        minArrivalTimeText.setBounds(50, 330, 150, 40);
        minArrivalTimeText.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 17));
        maxArrivalTimeText.setBounds(50, 390, 155, 40);
        maxArrivalTimeText.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 17));
        numberOfClientsText.setBounds(50, 450, 150, 40);
        numberOfClientsText.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 17));
        numberOfQueuesText.setBounds(50, 510, 150, 40);
        numberOfQueuesText.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 17));
        simulationText.setBounds(400, 100, 150, 40);
        simulationText.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));

        timeLimitPanel.setBounds(215, 153, 60, 35);
        minServiceTimePanel.setBounds(215, 219, 60, 35);
        maxServiceTimePanel.setBounds(215, 279, 60, 35);
        minArrivalTimePanel.setBounds(215, 342, 60, 35);
        maxArrivalTimePanel.setBounds(215, 398, 60, 35);
        numberOfClientsPanel.setBounds(215, 459, 60, 35);
        numberOfQueuesPanel.setBounds(215, 519, 60, 35);
        scroll.setBounds(310, 153, 650, 400);
        simulationPanel.setBounds(310, 153, 650, 400);
        simulationPanel.setBorder(BorderFactory.createLineBorder(new Color(90, 22, 182), 1));

        inceput.setBounds(50, 620, 200, 50);
        oprire.setBounds(400, 620, 200, 50);
        iesire.setBounds(750, 620, 200, 50);
        inceput.setBackground(new Color(184, 143, 239));
        oprire.setBackground(new Color(184, 143, 239));
        iesire.setBackground(new Color(184, 143, 239));
        inceput.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
        oprire.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));
        iesire.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 18));

        simulationPanel.setEditable(false);
    }

    private void adaugaComponente() {
        add(cozileMagazinuluiText);
        add(timeLimitText);
        add(minServiceTimeText);
        add(maxServiceTimeText);
        add(minArrivalTimeText);
        add(maxArrivalTimeText);
        add(numberOfClientsText);
        add(numberOfQueuesText);
        add(simulationText);
        add(timeLimitPanel);
        add(minServiceTimePanel);
        add(maxServiceTimePanel);
        add(minArrivalTimePanel);
        add(maxArrivalTimePanel);
        add(numberOfClientsPanel);
        add(numberOfQueuesPanel);
        add(inceput);
        add(oprire);
        add(iesire);
        add(scroll);
    }

    private boolean verificarePanelGol() throws CasutaGoalaExceptie {
        if ((timeLimitPanel.getText().isEmpty() || (minArrivalTimePanel.getText().isEmpty()) || (maxArrivalTimePanel.getText().isEmpty()) || (maxServiceTimePanel.getText().isEmpty()) || (minServiceTimePanel.getText().isEmpty()) || (numberOfClientsPanel.getText().isEmpty()) || (numberOfQueuesPanel.getText().isEmpty()))) {
            throw new CasutaGoalaExceptie("Completati panelul care este gol!");
        }
        return false;
    }

    private boolean verificareDateIntroduse() throws DateGresiteExceptie {
        try {
            if (Integer.parseInt(timeLimitPanel.getText()) < 0 ||
                    Integer.parseInt(minServiceTimePanel.getText()) < 0 ||
                    Integer.parseInt(maxServiceTimePanel.getText()) < 0 ||
                    Integer.parseInt(minArrivalTimePanel.getText()) < 0 ||
                    Integer.parseInt(maxArrivalTimePanel.getText()) < 0 ||
                    Integer.parseInt(numberOfClientsPanel.getText()) < 0 ||
                    Integer.parseInt(numberOfQueuesPanel.getText()) < 0) {
                throw new DateGresiteExceptie("Nu ati introdus datele corect in panel!");
            }
        } catch (NumberFormatException ex1) {
            throw new DateGresiteExceptie("Nu puteti introduce litere!");
        }
        return false;
    }

    //NumberFormatException

    public void afisareText(int timp, List<Task> taskuri, List<Server> servere, int nrServere) {
        simulationPanel.append("Timp:"+timp + "\n");
        simulationPanel.append("In asteptare:");
        for (Task t:taskuri) {
            simulationPanel.append(t.toString() + " ");
        }
        simulationPanel.append("\n");

        for(int i=0;i<nrServere;i++){
            simulationPanel.append("Server["+ (i+1) +"]:");
            for (Server s:servere) {
                if(s.getIdServer()==i){
                    s.afisareTask(simulationPanel);
                }
            }
        }
        simulationPanel.append("\n");
    }

    public void scriereFisier(int peakHour, float timpServire, float timpAsteptare){
        simulationPanel.append("Ora de varf:" + peakHour + "\n");
        simulationPanel.append("Timp mediu servire:" + timpServire + "\n");
        simulationPanel.append("Timp mediu asteptare:" + timpAsteptare);
        try {
            file.write(simulationPanel.getText());
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void deschidereFisier() {
        try{
            file=new FileWriter("FisierLog.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean oprireFortata(){
        return o;
    }

    public SimulationFrame(String title) throws HeadlessException {
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
        ImageIcon image = new ImageIcon("LOGO.PNG");
        setIconImage(image.getImage());
        getContentPane().setBackground(Color.PINK);

        deschidereFisier();

        //actiune facuta de butonul de iesire e aceeasi ca a butonului X
        iesire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        inceput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!verificarePanelGol()) {
                        try {
                            if (!verificareDateIntroduse()) {
                                timpLimita = Integer.parseInt(timeLimitPanel.getText());
                                timpMinServire = Integer.parseInt(minServiceTimePanel.getText());
                                timpMaxServire = Integer.parseInt(maxServiceTimePanel.getText());
                                timpMinSosire = Integer.parseInt(minArrivalTimePanel.getText());
                                timpMaxSosire = Integer.parseInt(maxArrivalTimePanel.getText());
                                nrClienti = Integer.parseInt(numberOfClientsPanel.getText());
                                nrCozi = Integer.parseInt(numberOfQueuesPanel.getText());
                                SimulationManager gen = new SimulationManager(timpLimita, timpMinServire, timpMaxServire, timpMinSosire, timpMaxSosire, nrCozi, nrClienti);
                                gen.setFrame(frame);
                                Thread t = new Thread(gen);
                                t.start();
                            }
                        } catch (DateGresiteExceptie ex) {
                            JOptionPane.showMessageDialog(null, ex.getMessage(), "Atentie, date gresite!", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (CasutaGoalaExceptie ex1) {
                    JOptionPane.showMessageDialog(null, ex1.getMessage(), "Atentie, panel gol!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        oprire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                o=true;
            }
        });
    }
}
