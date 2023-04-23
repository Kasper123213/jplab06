package ship;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class ButtonsPanel extends JPanel {
    JButton scann = new JButton("Scan");

    public ButtonsPanel(int buttonWidth, int buttonHeight) {
        setLayout(null);
        scann.setSize(buttonWidth, buttonHeight);
        add(scann);

        scann.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Ship.scan();
                } catch (IOException | ClassNotFoundException ex) {
                }
            }
        });

    }

    public void setButton() {
        scann.setEnabled(false);
    }
}