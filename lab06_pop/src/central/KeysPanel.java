package central;

import javax.swing.*;
import java.awt.*;

class KeysPanel extends JPanel {

    JLabel[] labels = new JLabel[9];
    private int height;
    private int size = 8;

    public KeysPanel(int width, int height) {
        this.height = height;
        setBackground(Color.gray);
        setLayout(new GridLayout(9, 1));

        for (int i = 0; i <= 8; i++) {
            labels[i] = new JLabel(String.valueOf(i));
            labels[i].setFont(new Font("0", 10, (int) (height / size)));

            add(labels[i]);
        }

        repaint();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int i = 0; i <= 8; i++) {
            g.setColor(new Color(0, 0, (8 - i) * 25 + 55));
            g.fillRect((int) (height / size), labels[i].getY(), height / size, height / size);
        }
    }
}
