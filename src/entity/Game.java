package entity;

public class Game {
    private Player[] players;
    private Map[] maps;

    public Game(Player[] players, Map[] maps) {
        this.players = players;
        this.maps = maps;
    }

    public Game() {
        this.players = new Player[2];
        this.maps = new Map[2];
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
