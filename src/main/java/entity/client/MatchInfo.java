package entity.client;

import entity.db.Player;
import entity.db.Team;

import java.util.Date;
import java.util.List;

public class MatchInfo {

    private int idMatch;
    private Team homeTeam;
    private Team guestTeam;
    private List<Player> homeTeamPlayers;
    private List<Player> guestTeamPlayers;
    private Date date;
    private int homeGoals;
    private int guestGoals;

    public int getGuestGoals() {
        return guestGoals;
    }

    public void setGuestGoals(int guestGoals) {
        this.guestGoals = guestGoals;
    }

    public int getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(int idMatch) {
        this.idMatch = idMatch;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getGuestTeam() {
        return guestTeam;
    }

    public void setGuestTeam(Team guestTeam) {
        this.guestTeam = guestTeam;
    }

    public List<Player> getHomeTeamPlayers() {
        return homeTeamPlayers;
    }

    public void setHomeTeamPlayers(List<Player> homeTeamPlayers) {
        this.homeTeamPlayers = homeTeamPlayers;
    }

    public List<Player> getGuestTeamPlayers() {
        return guestTeamPlayers;
    }

    public void setGuestTeamPlayers(List<Player> guestTeamPlayers) {
        this.guestTeamPlayers = guestTeamPlayers;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

}
