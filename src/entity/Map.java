package entity;

public class Map {
    private int[][] map;
    private Ship[] ships;

    public Map(int[][] map, Ship[] ships) {
        this.map = map;
        this.ships = ships;
    }

    public Map() {
        this.map = new int[16][16];
        this.ships = new Ship[21];
    }
    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public Ship[] getShips() {
        return ships;
    }

    public void setShips(Ship[] ships) {
        this.ships = ships;
    }
}
