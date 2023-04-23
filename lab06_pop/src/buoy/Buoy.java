package buoy;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
/*
 * @author Kasper Radom 264023
 *
 * budowanie:
 * 	dir /b /s *.java > sources.txt
 * kompilacj:
 * 	javac -d bin @sources.txt
 * archiwizacja:
 * 	xcopy src bin /e
 * 	java csv lab06_pop.jar -C bin .
 * uruchamianie:
 * 	java -jar lab06_pop.jar
 */
public class Buoy {
    protected static int id;
    protected int x;
    protected int y;
    protected static int value = 0;
    private static int centralPort = 10001;
    private static int worldPort = 10011;
    private static int shipsInfoPort = 10012;

    public Buoy() {
        try {
            setId();
            sendValue();
        } catch (IOException | ClassNotFoundException e) {
        }


        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        askWorld();
                        Thread.sleep(500);
                    } catch (InterruptedException | RuntimeException | ClassNotFoundException | IOException e) {}
                }
            }
        });
        t.start();
    }

    private static void askWorld() throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", worldPort);


        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        String message = "" + id;
        out.writeObject(message);

        value = (int) in.readObject();
        sendValue();

        socket.close();
    }


    private static void sendValue() throws IOException, ClassNotFoundException {

        Socket socket = new Socket("localhost", centralPort);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        int[] message = new int[2];
        message[0] = id;
        message[1] = value;
        out.writeObject(message);

        socket.close();


    }

    private static void setId() throws IOException, ClassNotFoundException {


        Socket socket = new Socket("localhost", centralPort);

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        out.writeObject("setId");

        id = (int) in.readObject();

        socket.close();


    }
}

