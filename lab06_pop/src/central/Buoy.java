package central;

class Buoy {
    private int x;
    private int y;
    private int id;
    private int value = 0;

    public Buoy(int id) {
        this.id = id;
        x = (id % 8) * 5 + 2;
        y = (int) (id / 8) * 5 + 2;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
