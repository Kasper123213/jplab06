package ship;


import javax.swing.*;
import java.awt.*;

class ScannPanel extends JPanel {

    protected int[][] sectors = new int[40][40];
    private int side;

    public ScannPanel(int width, int height) {

        setBackground(new Color(200, 200, 200));

        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                sectors[i][j] = 0;
            }
        }
        side = width < height ? width / 40 : height / 40;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.translate(10, 10);
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                if (sectors[i][j] == 0) {// TODO: 04.01.2023 dodac wlasny statek na żółto
                    g.setColor(Color.blue);
                } else if (j == Ship.getX() && i == Ship.getY()) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(Color.red);
                }
                g.fillRect(j * side, i * side, side, side);
            }
        }
    }

    public void setShips(int[] ships) {
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                sectors[i][j] = 0;
            }
        }

        for (int i : ships) {
            sectors[(int) (i / 40)][i % 40] = 1;
        }

        repaint();
    }


}
