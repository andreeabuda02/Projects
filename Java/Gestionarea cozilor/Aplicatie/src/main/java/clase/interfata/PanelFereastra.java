package clase.interfata;

import javax.swing.*;
import java.awt.*;

class PanelFereastra extends JPanel {
    private Image imagineDeFundal;

    public PanelFereastra() {
        ImageIcon img = new ImageIcon("Purple.jpg");
        imagineDeFundal = img.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagineDeFundal, 0, 0, getWidth(), getHeight(), this);
    }
}
