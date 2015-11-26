package entity.client;

import entity.db.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamInfo {

    private int idTeam;
    private String name;
    private List<Player> players;

    public TeamInfo(int idTeam, String name) {
        this.idTeam = idTeam;
        this.name = name;
        this.players = new ArrayList<Player>();
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

}
