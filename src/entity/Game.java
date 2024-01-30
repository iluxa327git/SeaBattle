package entity;

public class Game {
    private Player[] players = new Player[2];
    private Map[] maps = new Map[2];

    public Game(Player[] players, Map[] maps) {
        this.players = players;
        this.maps = maps;
    }

    public Game() {
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public Map[] getMaps() {
        return maps;
    }

    public void setMaps(Map[] maps) {
        this.maps = maps;
    }
}
