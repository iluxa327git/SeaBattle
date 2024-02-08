package entity;

import java.util.HashMap;

public class Match {
    private java.util.Map<Player, Map[]> match;

    public Match() {
        this.match = new HashMap<Player, Map[]>();
    }

    public java.util.Map<Player, Map[]> getMatch() {
        return match;
    }

    public void setMatch(java.util.Map<Player, Map[]> match) {
        this.match = match;
    }
}