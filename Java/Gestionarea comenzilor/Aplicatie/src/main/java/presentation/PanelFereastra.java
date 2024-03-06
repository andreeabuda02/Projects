package presentation;


import javax.swing.*;
import java.awt.*;

class PanelFereastra extends JPanel {
    private Image imagineDeFundal;

    /**
     * Această metodă este constructorul clasei PanelFereastra.
     * Inițializează imaginea de fundal cu o imagine dată.
     */
    public PanelFereastra() {
        ImageIcon img = new ImageIcon("Purple.jpg");
        imagineDeFundal = img.getImage();
    }

    /**
     * Suprascrie metoda paintComponent din clasa JPanel.
     * Desenează imaginea de fundal pe componenta curentă.
     *
     * @param g Obiectul Graphics utilizat pentru desenare.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagineDeFundal, 0, 0, getWidth(), getHeight(), this);
    }
}
