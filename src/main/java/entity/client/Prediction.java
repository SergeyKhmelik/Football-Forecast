package entity.client;

import java.io.Serializable;

public class Prediction implements Serializable{


    private static final long serialVersionUID = 1608490805181511695L;
    private MatchInfo matchInfo;
    private double homeTeamChance;
    private double guestTeamChance;

    public double getHomeTeamChance() {
        return homeTeamChance;
    }

    public void setHomeTeamChance(double homeTeamChance) {
        this.homeTeamChance = homeTeamChance;
    }

    public double getGuestTeamChance() {
        return guestTeamChance;
    }

    public void setGuestTeamChance(double guestTeamChance) {
        this.guestTeamChance = guestTeamChance;
    }

    public MatchInfo getMatchInfo() {
        return matchInfo;
    }

    public void setMatchInfo(MatchInfo matchInfo) {
        this.matchInfo = matchInfo;
    }

}
