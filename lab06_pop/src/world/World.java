package world;


import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


class World {
    private static int[][] wave = {{0, 1, 2, 1, 0},
            {1, 2, 3, 2, 1},
            {2, 3, 4, 3, 2},
            {1, 2, 3, 2, 1},
            {0, 1, 2, 1, 0}};
    static ArrayList<Ship> ships = new ArrayList<Ship>();
    private static int buoyPort = 10011;
    private static int shipPort = 10021;
    private static int sendToBuoyPort = 10012;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WorldFrame();
            }
        });
        startBuoyService();
        startShipService();


    }

    private static void startShipService() {
        Thread shipService = new Thread(new Runnable() {
            @Override
            public void run() {


                ServerSocket listener = null;
                try {
                    listener = new ServerSocket(shipPort);
                } catch (IOException e) {
                }


                while (true) {
                    if (WorldFrame.wp != null) {
                        WorldFrame.wp.repaint();
                    }

                    while (true) {

                        try {
                            Socket clientSocket = listener.accept();

                            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());


                            Object ob = in.readObject();
                            if (ob == null) {
                                int s = ships.size();

                                boolean searching = true;
                                int x = 0;
                                while (searching) {
                                    searching = false;
                                    x = (int) (Math.random() * 40);
                                    for (Ship ship : ships) {
                                        if (ship.getX() == x && ship.getLive()) searching = true;
                                    }
                                }

                                int y = 0;
                                searching = true;
                                while (searching) {
                                    searching = false;
                                    y = (int) (Math.random() * 40);
                                    for (Ship ship : ships) {
                                        if (ship.getY() == y && ship.getLive()) searching = true;
                                    }
                                }
                                int[] answer = new int[3];
                                answer[0] = s;
                                answer[1] = x;
                                answer[2] = y;
                                out.writeObject(answer);
                                ships.add(new Ship(x, y, s));
                                WorldFrame.wp.repaint();


                            } else if (ob instanceof int[]) {


                                int[] newPossition = (int[]) ob;
                                boolean isAlive = true;
                                for (Ship ship : ships) {
                                    if (ship.getId() != newPossition[0] &&
                                            ship.getX() == newPossition[1] &&
                                            ship.getY() == newPossition[2] &&
                                            ship.getLive()) {
                                        ship.kill();
                                        ships.get(newPossition[0]).kill();
                                        isAlive = false;
                                    }
                                }
                                if (!(newPossition[1] < 0 ||
                                        newPossition[1] >= 40 ||
                                        newPossition[2] < 0 ||
                                        newPossition[2] >= 40) && isAlive) {
                                    try {
                                        out.writeObject(0);

                                        ships.get(newPossition[0]).setX(newPossition[1]);
                                        ships.get(newPossition[0]).setY(newPossition[2]);
                                        WorldFrame.wp.repaint();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    } catch (IndexOutOfBoundsException e) {
                                    }
                                } else if (isAlive) {
                                    out.writeObject(1);
                                } else out.writeObject(2);


                            } else if (ob instanceof String) {
                                int[] shipList = new int[ships.size()];
                                for (int i = 0; i < ships.size(); i++) {
                                    shipList[i] = ships.get(i).getNumber();
                                }
                                out.writeObject(shipList);


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
        shipService.start();
    }


    private static void startBuoyService() {
        Thread buoyService = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (WorldFrame.wp != null) {
                        WorldFrame.wp.repaint();
                    }
                    ServerSocket listener = null;
                    try {
                        listener = new ServerSocket(buoyPort);
                    } catch (IOException e) {
                    }
                    if (listener == null) continue;

                    while (true) {
                        try {

                            Socket clientSocket = listener.accept();

                            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

                            int id = Integer.parseInt((String) in.readObject());
                            int value = 0;

                            int buouX = (int) (id / 8);
                            int buouY = id % 8;

                            for (Ship ship : ships) {
                                if (ship.getX() >= buouX - 2 && ship.getX() <= buouX + 2 &&
                                        ship.getY() >= buouY - 2 && ship.getY() <= buouY + 2 && ship.getLive()) {
                                    value += wave[buouY - ship.getY() + 2][buouX - ship.getX() + 2];
                                }
                            }

                            out.writeObject(value);
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
