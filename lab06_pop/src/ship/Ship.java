package ship;


import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class Ship {
    private static int x;
    private static int y;
    private static int id;
    private static boolean alive = true;
    private static int worldPort = 10021;

    public static void main(String[] args) {

        try {
            setId();
        } catch (IOException | ClassNotFoundException e) {
        }

        ShipFrame.setTitle(id);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ShipFrame();
            }
        });


        Thread moving = new Thread(new Runnable() {
            @Override
            public void run() {
                while (alive) {
                    try {
                        Thread.sleep(1000 + (int) (Math.random() * 500));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    findDirection();
                }


            }
        });
        moving.start();
    }


    public static void findDirection() {
        int direction = (int) (Math.random() * 7);
        int newX = x;
        int newY = y;
        switch (direction) {
            case 0:
                newX -= 1;
                newY -= 1;
                break;
            case 1:
                newY -= 1;
                break;
            case 2:
                newX += 1;
                newY -= 1;
                break;
            case 3:
                newX += 1;
                break;
            case 4:
                newX += 1;
                newY += 1;
                break;
            case 5:
                newY += 1;
                break;
            case 6:
                newX -= 1;
                newY += 1;
                break;
            case 7:
                newX -= 1;
                break;
        }

        try {
            move(newX, newY);
        } catch (IOException | ClassNotFoundException e) {
        }
    }

    public static void move(int newX, int newY) throws IOException, ClassNotFoundException {

        Socket socket = new Socket("localhost", worldPort);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        int[] newCoords = new int[3];
        newCoords[0] = id;
        newCoords[1] = newX;
        newCoords[2] = newY;
        out.writeObject(newCoords);

        int answer = (int) in.readObject();
        if (answer == 0) {
            x = newX;
            y = newY;
        } else if (answer == 2) {
            alive = false;
            ShipFrame.bp.setButton();
        }
        socket.close();
    }

    public static void scan() throws IOException, ClassNotFoundException {

        Socket socket = new Socket("localhost", worldPort);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        out.writeObject("scan");

        ShipFrame.sp.setShips((int[]) in.readObject());

        socket.close();
    }


    private static void setId() throws IOException, ClassNotFoundException {


        Socket socket = new Socket("localhost", worldPort);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        out.writeObject(null);

        int[] answer = (int[]) in.readObject();
        id = answer[0];
        x = answer[1];
        y = answer[2];

        socket.close();


    }

    public static int getX() {
        return x;
    }

    public static int getY() {
        return y;
    }
}
