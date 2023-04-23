import buoy.Buoy;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Buoy> b = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 64; i++) {
                    b.add(new Buoy());
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("done" + i);
                }
            }
        });
        t.start();
    }
}
