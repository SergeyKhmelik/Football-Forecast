package dao.mysql;

import dao.ForecastDao;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MysqlForecastDao implements ForecastDao {

    private static final Logger LOGGER = Logger.getLogger(MysqlForecastDao.class);

    private static final String GET_MATCHES_BY_TEAM_AND_DATE =
            "SELECT IF(idHomeTeam=?, home_goals - guest_goals, guest_goals - home_goals) as score " +
            "FROM football_forecast.match " +
            "WHERE idSeason=(SELECT idSeason FROM season WHERE DATE(?) BETWEEN season.start AND season.end) " +
            "AND (idHomeTeam=? OR idGuestTeam=?) " +
            "AND date<?" +
            "ORDER BY football_forecast.match.date";
    private static final String GET_MATCHES_BY_TEAM_AND_OPPONENT =
            "SELECT IF(idHomeTeam=?, home_goals - guest_goals, guest_goals - home_goals) as score " +
            "FROM football_forecast.match " +
            "WHERE (idHomeTeam=? OR idGuestTeam=?) " +
            "AND (idHomeTeam=? OR idGuestTeam=?) " +
            "AND date<?" +
            "ORDER BY football_forecast.match.date";
    private static final String GET_MATCHES_BY_TEAM_AT_HOME =
            "SELECT IF(idHomeTeam=?, home_goals - guest_goals, guest_goals - home_goals) as score " +
            "FROM football_forecast.match " +
            "WHERE idSeason=(SELECT idSeason FROM season WHERE DATE(?) BETWEEN season.start AND season.end) " +
            "AND idHomeTeam=? " +
            "AND date<?" +
            "ORDER BY football_forecast.match.date";
    private static final String GET_MATCHES_BY_TEAM_AS_GUEST =
            "SELECT IF(idHomeTeam=?, home_goals - guest_goals, guest_goals - home_goals) as score " +
            "FROM football_forecast.match " +
            "WHERE idSeason=(SELECT idSeason FROM season WHERE DATE(?) BETWEEN season.start AND season.end) " +
            "AND idGuestTeam=? " +
            "AND date<?" +
            "ORDER BY football_forecast.match.date";


    @Override
    public List<Integer> getMatchesByTeamInCurrentSeason(Connection conn, int idTeam, Date date) throws SQLException {
        List<Integer> result = new ArrayList<Integer>();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        try(PreparedStatement pstm = conn.prepareStatement(GET_MATCHES_BY_TEAM_AND_DATE)){
            pstm.setInt(1, idTeam);
            pstm.setDate(2, sqlDate);
            pstm.setInt(3, idTeam);
            pstm.setInt(4, idTeam);
            pstm.setDate(5, sqlDate);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                result.add(rs.getInt(1));
            }
        }  catch (SQLException ex) {
            LOGGER.error("Cannot find championships ", ex);
            throw ex;
        }
        return result;
    }

    @Override
    public List<Integer> getMatchesByTeamAgainstOpponent(Connection conn, int idTeam, int idOpponent, Date date) throws SQLException {
        List<Integer> result = new ArrayList<Integer>();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        try(PreparedStatement pstm = conn.prepareStatement(GET_MATCHES_BY_TEAM_AND_OPPONENT)){
            pstm.setInt(1, idTeam);
            pstm.setInt(2, idTeam);
            pstm.setInt(3, idTeam);
            pstm.setInt(4, idOpponent);
            pstm.setInt(5, idOpponent);
            pstm.setDate(6, sqlDate);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                result.add(rs.getInt(1));
            }
        }  catch (SQLException ex) {
            LOGGER.error("Cannot find championships ", ex);
            throw ex;
        }
        return result;
    }

    @Override
    public List<Integer> getHomeMatchesByTeam(Connection conn, int idTeam, Date date) throws SQLException {
        List<Integer> result = new ArrayList<Integer>();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        try(PreparedStatement pstm = conn.prepareStatement(GET_MATCHES_BY_TEAM_AT_HOME)){
            pstm.setInt(1, idTeam);
            pstm.setDate(2, sqlDate);
            pstm.setInt(3, idTeam);
            pstm.setDate(4, sqlDate);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                result.add(rs.getInt(1));
            }
        }  catch (SQLException ex) {
            LOGGER.error("Cannot find championships ", ex);
            throw ex;
        }
        return result;
    }

    @Override
    public List<Integer> getGuestMatchesByTeam(Connection conn, int idTeam, Date date) throws SQLException {
        List<Integer> result = new ArrayList<Integer>();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        try(PreparedStatement pstm = conn.prepareStatement(GET_MATCHES_BY_TEAM_AS_GUEST)){
            pstm.setInt(1, idTeam);
            pstm.setDate(2, sqlDate);
            pstm.setInt(3, idTeam);
            pstm.setDate(4, sqlDate);
            ResultSet rs = pstm.executeQuery();
            while(rs.next()){
                result.add(rs.getInt(1));
            }
        }  catch (SQLException ex) {
            LOGGER.error("Cannot find championships ", ex);
            throw ex;
        }
        return result;
    }
}
