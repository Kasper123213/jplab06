package central;


import javax.swing.*;
import java.awt.*;

class CentralFrame extends JFrame {
    static CentralPanel cp;
    KeysPanel kp;
    int width = 350;
    int height = 300;
    double proportion = 0.83;
    JPanel contentPane;

    public CentralFrame() {

        contentPane = new JPanel();
        contentPane.setLayout(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(width + 10, height + 35));
        setContentPane(contentPane);
        setVisible(true);
        setTitle("Central");
        setAlwaysOnTop(true);
        cp = new CentralPanel((int) (width * proportion - 10), height - 10);
        cp.setBounds(5, 5, (int) (width * proportion - 10), height - 10);

        contentPane.add(cp);

        kp = new KeysPanel((int) (width * (1 - proportion)), height - 20);
        kp.setBounds((int) (width * proportion), 5, (int) (width * (1 - proportion) - 5), height - 20);

        contentPane.add(kp);

    }
}
