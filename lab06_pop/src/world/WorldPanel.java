package world;

import javax.swing.*;
import java.awt.*;

class WorldPanel extends JPanel {
    protected int[][] sectors = new int[40][40];
    private int side;

    public WorldPanel(int width, int height) {
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


        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                sectors[i][j] = 0;
            }
        }


        for (Ship ship : World.ships) {
            if (ship.getLive()) sectors[ship.getX()][ship.getY()] = 1;
        }
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                if (sectors[i][j] == 0) {
                    g.setColor(Color.blue);
                } else {
                    g.setColor(Color.red);
                }
                g.fillRect(i * side, j * side, side, side);
            }
        }
    }


}
