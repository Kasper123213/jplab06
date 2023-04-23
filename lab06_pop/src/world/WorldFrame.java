package world;

import javax.swing.*;
import java.awt.*;

class WorldFrame extends JFrame {


    private JPanel contentPane;
    protected static WorldPanel wp;


    private final int width = 300;
    private final int height = 300;

    public WorldFrame() {

        contentPane = new JPanel();
        contentPane.setLayout(null);

        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(width + 10, height + 35));
        setContentPane(contentPane);
        setVisible(true);
        setTitle("World");

        wp = new WorldPanel(width - 10, height - 10);
        wp.setBounds(5, 5, width - 10, height - 10);


        contentPane.add(wp);


    }

}

