package ship;

import javax.swing.*;
import java.awt.*;

class ShipFrame extends JFrame {


    private JPanel contentPane;
    public static ButtonsPanel bp;
    public static ScannPanel sp;


    private final int width = 305;
    private final int height = 330;
    private final double proportions = 0.9;

    private static String title= "Ship ";
    public ShipFrame() {

        contentPane = new JPanel();
        contentPane.setLayout(null);

        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(width + 10, height + 35));
        setContentPane(contentPane);
        setVisible(true);
        setTitle(title);

        sp = new ScannPanel((width),height);
        sp.setBounds(0, 0, width , (int) (height * proportions));

        bp = new ButtonsPanel(width,(int) (height - height * proportions));
        bp.setBounds(0, (int) (height * proportions), width,height);


        contentPane.add(sp);
        contentPane.add(bp);


    }

    protected static void setTitle(int id){
        title+=String.valueOf(id);
    }

}