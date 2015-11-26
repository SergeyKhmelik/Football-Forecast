package entity;

import entity.db.Player;

import java.util.Comparator;

public class PlayersPositionComparator implements Comparator<Player> {

    @Override
    public int compare(Player o1, Player o2) {
        return o1.getPosition().compareTo(o2.getPosition());
    }

}
