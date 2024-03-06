package clase.interfata;
import clase.model.Operatii;
import clase.model.Polinom;
import clase.utils.PolinomPattern;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class Interfata extends JFrame {
    //label-uri
    private JLabel labelCalculator=new JLabel("Calculator Polinomial");
    private JLabel labelPolinom1=new JLabel("Polinom 1:");
    private JLabel labelPolinom2=new JLabel("Polinom 2:");
    private JLabel labelRezultat = new JLabel("Rezultat:");

    //casutele pentru textul polinoamelor
    private JTextField polinom1Panel=new JTextField();
    private JTextField polinom2Panel=new JTextField();
    private JTextArea rezultatPanel=new JTextArea();

    //butoane
    private JRadioButton p1=new JRadioButton();
    private JRadioButton p2=new JRadioButton();
    private JButton adunare = new JButton("Adunare");
    private JButton scadere = new JButton("Scadere");
    private JButton inmultire = new JButton("Inmultire");
    private JButton impartire = new JButton("Impartire");
    private JButton derivare = new JButton("Derivare");
    private JButton integrare = new JButton("Integrare");
    private JButton iesire = new JButton("EXIT");

    //polinoame
    private Polinom polinom1=new Polinom();
    private Polinom polinom2=new Polinom();
    private Polinom rezultat;
    private Operatii calcul=new Operatii();
    private PolinomPattern pattern=new PolinomPattern();
    public void aspectInterfata() {

        p1.setBounds(53, 102, 20, 20);
        p2.setBounds(53, 162, 20, 20);
        p1.setBackground(Color.PINK);
        p2.setBackground(Color.PINK);
        labelPolinom1.setBounds(80, 96, 100, 30);
        labelPolinom2.setBounds(80, 156, 100, 30);
        labelRezultat.setBounds(88, 216, 100, 30);
        labelCalculator.setBounds(125, 30, 300, 35);
        labelCalculator.setBorder(BorderFactory.createLineBorder(new Color(184, 143, 239), 2));
        polinom1Panel.setBounds(170, 100, 250, 30);
        polinom2Panel.setBounds(170, 160, 250, 30);
        rezultatPanel.setBounds(170, 220, 300, 60);
        polinom1Panel.setBorder(BorderFactory.createLineBorder(new Color(184, 143, 239), 1));
        polinom2Panel.setBorder(BorderFactory.createLineBorder(new Color(184, 143, 239), 1));
        rezultatPanel.setBorder(BorderFactory.createLineBorder(new Color(184, 143, 239), 1));
        labelCalculator.setForeground(new Color(90, 14, 204));
        labelRezultat.setForeground(new Color(39, 46, 192));
        labelPolinom1.setForeground(new Color(68, 19, 129));
        labelPolinom2.setForeground(new Color(68, 19, 129));
        labelCalculator.setFont(new Font("Georgia", Font.BOLD|Font.ITALIC , 25));
        labelPolinom1.setFont(new Font("Century Gothic",Font.BOLD|Font.ITALIC, 16));
        labelPolinom2.setFont(new Font("Century Gothic",Font.BOLD|Font.ITALIC, 16));
        labelRezultat.setFont(new Font("Century Gothic",Font.BOLD|Font.ITALIC, 16));

        adunare.setBounds(50, 290, 120, 30);
        scadere.setBounds(190, 290, 120, 30);
        inmultire.setBounds(330, 290, 120, 30);
        impartire.setBounds(50, 350, 120, 30);
        derivare.setBounds(190, 350, 120, 30);
        integrare.setBounds(330, 350, 120, 30);
        iesire.setBounds(190, 420, 120, 30);
        adunare.setBackground(new Color(184, 143, 239));
        scadere.setBackground(new Color(184, 143, 239));
        inmultire.setBackground(new Color(184, 143, 239));
        impartire.setBackground(new Color(184, 143, 239));
        derivare.setBackground(new Color(184, 143, 239));
        integrare.setBackground(new Color(184, 143, 239));
        iesire.setBackground(new Color(237, 195, 246));
        rezultatPanel.setEditable(false);
    }

    private void resetarePolinoame() {
        polinom1.getTermeni().clear();
        polinom2.getTermeni().clear();
    }

    private void afisareRezultat() {
        if (!rezultat.getTermeni().isEmpty())
            rezultatPanel.setText(rezultat.toString());
        else
            rezultatPanel.setText("0");
    }

    private void adaugaComponente() {
        add(polinom1Panel);add(polinom2Panel);add(rezultatPanel);
        add(labelPolinom1);add(labelPolinom2);add(labelRezultat);add(labelCalculator);add(adunare);add(scadere);add(inmultire);add(impartire);add(derivare);add(integrare);add(iesire);
        add(p1);add(p2);
    }
    private boolean verificarePanelGol() throws CasutaGoalaExceptie {
        if(( polinom1Panel.getText().isEmpty() || (polinom2Panel.getText().isEmpty()))){
            throw new CasutaGoalaExceptie("Completati panelul care este gol!");
        }
        return false;
    }
    private boolean verificarePanelGolIntegrareDerivare() throws CasutaGoalaExceptie{
        if((p1.isSelected() && polinom1Panel.getText().isEmpty() || (p2.isSelected()&&polinom2Panel.getText().isEmpty()))){
            throw new CasutaGoalaExceptie("Completati panelul selectat!");
        }
        return false;
    }
    public Interfata(String title) throws HeadlessException {
        //initializare frame
        super(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        //butoane in frame
        aspectInterfata();
        adaugaComponente();
        //adauga panel in frame si pozitionarea la mijlocul ecranului si afisarea lui
        ImageIcon img = new ImageIcon("FULL.jpg");
        JPanel fereastraP=new PanelFereastra();
        fereastraP.setPreferredSize(new Dimension(520, 520));
        add(fereastraP);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        ImageIcon image = new ImageIcon("LOGO.jpg");
        setIconImage(image.getImage());
        getContentPane().setBackground(Color.PINK);

        //adunare
        adunare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(!verificarePanelGol()){
                        resetarePolinoame();
                        pattern.crearePolinom(polinom1Panel.getText(),polinom1);
                        pattern.crearePolinom(polinom2Panel.getText(),polinom2);
                        rezultat = calcul.adunarePolinoame(polinom1,polinom2);
                        afisareRezultat();
                    }
                }catch (CasutaGoalaExceptie ex1){
                    JOptionPane.showMessageDialog(null, ex1.getMessage(), "Atentie panel gol!",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //scadere
        scadere.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(!verificarePanelGol()){
                        resetarePolinoame();
                        pattern.crearePolinom(polinom1Panel.getText(),polinom1);
                        pattern.crearePolinom(polinom2Panel.getText(),polinom2);
                        rezultat = calcul.scaderePolinoame(polinom1,polinom2);
                        afisareRezultat();
                    }
                }catch (CasutaGoalaExceptie ex1){
                    JOptionPane.showMessageDialog(null, ex1.getMessage(), "Atentie panel gol!",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //inmultire
        inmultire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(!verificarePanelGol()){
                        resetarePolinoame();
                        pattern.crearePolinom(polinom1Panel.getText(),polinom1);
                        pattern.crearePolinom(polinom2Panel.getText(),polinom2);
                        rezultat = calcul.inmultirePolinoame(polinom1,polinom2);
                        afisareRezultat();
                    }
                }catch (CasutaGoalaExceptie ex1){
                    JOptionPane.showMessageDialog(null, ex1.getMessage(), "Atentie panel gol!",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //impartire
        impartire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(!verificarePanelGol()){
                        resetarePolinoame();
                        Polinom rest = new Polinom();
                        pattern.crearePolinom(polinom1Panel.getText(),polinom1);
                        pattern.crearePolinom(polinom2Panel.getText(),polinom2);
                        Polinom[] rezultat = calcul.impartirePolinoame(polinom1, polinom2);
                        afisareRezultatImpartire(rezultat[0], rezultat[1]);
                    }
                }catch (CasutaGoalaExceptie ex1){
                    JOptionPane.showMessageDialog(null, ex1.getMessage(), "Atentie panel gol!",JOptionPane.ERROR_MESSAGE);
                }
                catch (ArithmeticException ex2){
                    JOptionPane.showMessageDialog(null, ex2.getMessage(), "Exceptie!",JOptionPane.ERROR_MESSAGE);
                }
            }
            private void afisareRezultatImpartire(Polinom cat, Polinom rest) {
                rezultatPanel.setText(cat.toString() + "\n");
                rezultatPanel.append(rest.toString());
            }
        });

        //derivare
        derivare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(p1.isSelected() || p2.isSelected()){
                        if(!verificarePanelGolIntegrareDerivare()) {
                            resetarePolinoame();
                            if (p1.isSelected()) {
                                pattern.crearePolinom(polinom1Panel.getText(), polinom1);
                                rezultat = calcul.derivarePolinom(polinom1);
                            } else if (p2.isSelected()) {
                                pattern.crearePolinom(polinom2Panel.getText(), polinom2);
                                rezultat = calcul.derivarePolinom(polinom2);
                            }
                            afisareRezultat();
                        }
                    }
                    else
                        throw new PolinomNeselectatExceptie("Selectati polinomul asupra caruia sa se efectueze operatia!");
                }catch (CasutaGoalaExceptie ex1){
                    JOptionPane.showMessageDialog(null, ex1.getMessage(), "Aveti grija!",JOptionPane.ERROR_MESSAGE);
                } catch (PolinomNeselectatExceptie ex2) {
                    JOptionPane.showMessageDialog(null, ex2.getMessage(), "Aveti grija!",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //integrare
        integrare.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(p1.isSelected() || p2.isSelected()){
                        if(!verificarePanelGolIntegrareDerivare()){
                            resetarePolinoame();
                            if(p1.isSelected()){
                                pattern.crearePolinom(polinom1Panel.getText(),polinom1);
                                rezultat=calcul.integrarePolinom(polinom1);
                            }
                            else if(p2.isSelected()){
                                pattern.crearePolinom(polinom2Panel.getText(), polinom2);
                                rezultat=calcul.integrarePolinom(polinom2);
                            }
                            afisareRezultat();
                        }
                    }
                    else
                        throw new PolinomNeselectatExceptie("Selectati polinomul asupra caruia sa se efectueze operatia!");
                }catch (CasutaGoalaExceptie ex1){
                    JOptionPane.showMessageDialog(null, ex1.getMessage(), "Aveti grija!",JOptionPane.ERROR_MESSAGE);

                } catch (PolinomNeselectatExceptie ex2) {
                    JOptionPane.showMessageDialog(null, ex2.getMessage(), "Aveti grija!",JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //actiune facuta de butonul de iesire e aceeasi ca a butonului X
        iesire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //daca selectam polinomul 1, p2 e deselectat
        p1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(p1.isSelected()&&p2.isSelected()){
                    p2.setSelected(false);
                }
            }
        });

        //daca selectam polinomul 2, p1 e deselectat
        p2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(p2.isSelected()&&p1.isSelected()){
                    p1.setSelected(false);
                }
            }
        });
    }
}
