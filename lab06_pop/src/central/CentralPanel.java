package central;

import javax.swing.*;
import java.awt.*;

class CentralPanel extends JPanel {
    protected int[][] sectors = new int[40][40];
    private int side;

    public CentralPanel(int width, int height) {
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                sectors[i][j] = 0;
            }
        }
        side = width > height ? width / 40 : height / 40;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (Buoy buoy : Central.buoys) {
            sectors[buoy.getY()][buoy.getX()] = buoy.getValue();
        }

        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                g.setColor(new Color(0, 0, (8 - sectors[i][j]) * 25 + 55));
                g.fillRect(j * side, i * side, side, side);
                if (j % 5 == 0) {
                    g.setColor(Color.black);
                    g.drawLine(j * side, 0, j * side, 900);
                }
                if (i % 5 == 0) {
                    g.setColor(Color.black);
                    g.drawLine(0, i * side, 700, i * side);
                }
            }

        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

                g.setColor(Color.black);
                g.setFont(new Font(Font.DIALOG, 10, 15));
                g.drawString(String.valueOf(sectors[i][j]), (i * 5 + 2) * side, (j * 5 + 3) * side);
            }
        }
    }
}























