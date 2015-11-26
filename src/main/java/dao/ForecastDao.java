package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public interface ForecastDao {

    List<Integer> getMatchesByTeamInCurrentSeason(Connection conn, int idTeam, Date date) throws SQLException;

    List<Integer> getMatchesByTeamAgainstOpponent(Connection conn, int idTeam, int idOpponent, Date date) throws SQLException;

    List<Integer> getHomeMatchesByTeam(Connection conn, int idTeam, Date date) throws SQLException;

    List<Integer> getGuestMatchesByTeam(Connection conn, int idTeam, Date date) throws SQLException;
}
