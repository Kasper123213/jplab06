package world;

class Ship {
    private int x;
    private int y;
    private int id;
    private boolean live = true;

    public Ship(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getNumber() {
        return y * 40 + x;
    }


    public int getId() {
        return id;
    }

    public void kill() {
        live = false;
    }

    public boolean getLive() {
        return live;
    }
}
