package central;


import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class Central {
    private static int buoyPort = 10001;
    static ArrayList<Buoy> buoys = new ArrayList<Buoy>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CentralFrame();
            }
        });

        startBuoyService();
    }

    private static void startBuoyService() {
        Thread buoyService = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (CentralFrame.cp != null) {
                        CentralFrame.cp.repaint();
                    }
                    ServerSocket listener = null;
                    try {
                        listener = new ServerSocket(buoyPort);
                    } catch (IOException e) {
                    }


                    while (true) {
                        try {
                            Socket clientSocket = listener.accept();

                            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                            Object ob = in.readObject();
                            if (!(ob instanceof int[] message)) {
                                int s = buoys.size();
                                out.writeObject(s);
                                buoys.add(new Buoy(s));
                            } else {
                                try {
                                    buoys.get(message[0]).setValue(message[1]);
                                    CentralFrame.cp.repaint();
                                } catch (IndexOutOfBoundsException e) {
                                }
                            }

                            out.close();
                            in.close();
                            clientSocket.close();
                            break;
                        } catch (IOException | ClassNotFoundException e) {
                        }
                    }
                }
            }
        });
        buoyService.start();
    }
}
