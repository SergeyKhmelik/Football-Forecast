package dao.mysql;

import dao.FootballEventDao;
import entity.db.*;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlFootballEventDao implements FootballEventDao {

    private static final Logger LOGGER = Logger
            .getLogger(MysqlFootballEventDao.class);

    private static final String FIND_CHAMPIONSHIPS = "SELECT * FROM championship";
    private static final String FIND_SEASONS = "SELECT * FROM season";
    private static final String FIND_MATCHES = "SELECT * FROM football_forecast.match WHERE idChampionship=? AND idSeason=?";
    private static final String FIND_PLAYERS_BY_TEAM = "SELECT * FROM player WHERE idTeam=?";
    private static final String FIND_ADDED_PLAYERS_BY_TEAM_AND_MATCH = "SELECT * FROM football_forecast.player WHERE idTeam=? AND idPlayer IN (SELECT idPlayer FROM football_forecast.match_player WHERE idMatch=?)";
    private static final String FIND_NOT_ADDED_PLAYERS_BY_TEAM_AND_MATCH = "SELECT * FROM player WHERE idTeam=? AND idPlayer NOT IN (SELECT idPlayer FROM match_player WHERE idMatch=?)";
    private static final String FIND_TEAMS = "SELECT * FROM team";
    private static final String INSERT_MATCH = "INSERT INTO football_forecast.match (idHomeTeam, idGuestTeam, idChampionship, idSeason, date) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_MATCH = "UPDATE football_forecast.match SET date=?, home_goals=?, guest_goals=? WHERE idMatch=?";
    private static final String DELETE_MATCH = "DELETE FROM football_forecast.match WHERE idMatch=?";
    private static final String FIND_MATCH = "SELECT * FROM football_forecast.match WHERE idMatch=?";
    private static final String ADD_PLAYER_TO_MATCH = "INSERT INTO match_player (idMatch, idPlayer) VALUES (?, ?)";
    private static final String FIND_CHAMPIONSHIP_BY_ID = "SELECT * FROM championship WHERE idChampionship=?";
    private static final String FIND_SEASON_BY_ID = "SELECT * FROM season WHERE idSeason=?";
    private static final String FIND_TEAM_BY_ID = "SELECT * FROM team WHERE idTeam=?";
    private static final String REPLACE_PLAYER_AT_MATCH = "UPDATE match_player SET idPlayer=? WHERE idMatch=? AND idPlayer=?";

    @Override
    public List<Championship> findChampionships(Connection conn) throws SQLException {
        List<Championship> result = new ArrayList<Championship>();
        try (Statement stm = conn.createStatement()) {
            ResultSet rs = stm.executeQuery(FIND_CHAMPIONSHIPS);
            while (rs.next()) {
                Championship championship = new Championship();
                championship.setIdChampionship(rs.getInt(1));
                championship.setName(rs.getString(2));
                championship.setDescription(rs.getString(3));
                result.add(championship);
            }
        } catch (SQLException ex) {
            LOGGER.error("Cannot find championships ", ex);
            throw ex;
        }
        return result;
    }

    @Override
    public List<Season> findSeasons(Connection conn) throws SQLException {
        List<Season> result = new ArrayList<Season>();
        try (Statement stm = conn.createStatement()) {
            ResultSet rs = stm.executeQuery(FIND_SEASONS);
            while (rs.next()) {
                Season season = new Season();
                season.setIdSeason(rs.getInt(1));
                season.setName(rs.getString(2));
                season.setStart(rs.getDate(3));
                season.setEnd(rs.getDate(4));
                result.add(season);
            }
        } catch (SQLException ex) {
            LOGGER.error("Cannot find seasons ", ex);
            throw ex;
        }
        return result;
    }

    @Override
    public List<Match> findMatches(Connection conn, int idChampionship, int idSeason) throws SQLException {
        List<Match> result = new ArrayList<Match>();
        try (PreparedStatement pstm = conn.prepareStatement(FIND_MATCHES)) {
            pstm.setInt(1, idChampionship);
            pstm.setInt(2, idSeason);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Match match = new Match();
                match.setIdMatch(rs.getInt(1));
                match.setIdHomeTeam(rs.getInt(2));
                match.setIdGuestTeam(rs.getInt(3));
                match.setIdChampionship(rs.getInt(4));
                match.setIdSeason(rs.getInt(5));
                match.setDate(rs.getDate(6));
                match.setHomeGoals(rs.getInt(7));
                match.setGuestGoals(rs.getInt(8));
                result.add(match);
            }
        } catch (SQLException ex) {
            LOGGER.error("Cannot find matches ", ex);
            throw ex;
        }
        return result;
    }

    @Override
    public List<Player> findPlayers(Connection conn, int idTeam) throws SQLException {
        List<Player> result = new ArrayList<Player>();
        try (PreparedStatement pstm = conn.prepareStatement(FIND_PLAYERS_BY_TEAM)) {
            pstm.setInt(1, idTeam);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Player player = new Player();
                player.setIdPlayer(rs.getInt(1));
                player.setIdTeam(rs.getInt(2));
                player.setFirstname(rs.getString(3));
                player.setLastname(rs.getString(4));
                player.setNumber(rs.getInt(5));
                player.setPosition(rs.getString(6));
                player.setRank(rs.getDouble(7));
                result.add(player);
            }
        } catch (SQLException ex) {
            LOGGER.error("Cannot find players ", ex);
            throw ex;
        }
        return result;
    }

    @Override
    public List<Player> findAddedPlayers(Connection conn, int idTeam, int idMatch) throws SQLException {
        List<Player> result = new ArrayList<Player>();
        try (PreparedStatement pstm = conn.prepareStatement(FIND_ADDED_PLAYERS_BY_TEAM_AND_MATCH)) {
            pstm.setInt(1, idTeam);
            pstm.setInt(2, idMatch);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Player player = new Player();
                player.setIdPlayer(rs.getInt(1));
                player.setIdTeam(rs.getInt(2));
                player.setFirstname(rs.getString(3));
                player.setLastname(rs.getString(4));
                player.setNumber(rs.getInt(5));
                player.setPosition(rs.getString(6));
                player.setRank(rs.getDouble(7));
                result.add(player);
            }
        } catch (SQLException ex) {
            LOGGER.error("Cannot find players ", ex);
            throw ex;
        }
        return result;
    }

    @Override
    public List<Player> findNotAddedPlayers(Connection conn, int idTeam, int idMatch) throws SQLException {
        List<Player> result = new ArrayList<Player>();
        try (PreparedStatement pstm = conn.prepareStatement(FIND_NOT_ADDED_PLAYERS_BY_TEAM_AND_MATCH)) {
            pstm.setInt(1, idTeam);
            pstm.setInt(2, idMatch);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Player player = new Player();
                player.setIdPlayer(rs.getInt(1));
                player.setIdTeam(rs.getInt(2));
                player.setFirstname(rs.getString(3));
                player.setLastname(rs.getString(4));
                player.setNumber(rs.getInt(5));
                player.setPosition(rs.getString(6));
                player.setRank(rs.getDouble(7));
                result.add(player);
            }
        } catch (SQLException ex) {
            LOGGER.error("Cannot find players ", ex);
            throw ex;
        }
        return result;
    }

    @Override
    public List<Team> findTeams(Connection conn) throws SQLException {
        List<Team> result = new ArrayList<Team>();
        try (Statement stm = conn.createStatement()) {
            ResultSet rs = stm.executeQuery(FIND_TEAMS);
            while (rs.next()) {
                Team team = new Team();
                team.setIdTeam(rs.getInt(1));
                team.setName(rs.getString(2));
                result.add(team);
            }
        } catch (SQLException ex) {
            LOGGER.error("Cannot find teams ", ex);
            throw ex;
        }
        return result;
    }

    @Override
    public int insertMatch(Connection conn, Match match) throws SQLException {
        int autogeneratedId = 0;
        try (PreparedStatement pstm = conn.prepareStatement(INSERT_MATCH, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setInt(1, match.getIdHomeTeam());
            pstm.setInt(2, match.getIdGuestTeam());
            pstm.setInt(3, match.getIdChampionship());
            pstm.setInt(4, match.getIdSeason());
            pstm.setDate(5, match.getDate());
            pstm.executeUpdate();
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next()) {
                autogeneratedId = rs.getInt(1);
            }
        } catch (SQLException ex) {
            LOGGER.error("Cannot insert match ", ex);
            throw ex;
        }
        return autogeneratedId;
    }

    @Override
    public int updateMatch(Connection conn, Match match) throws SQLException {
        try (PreparedStatement pstm = conn.prepareStatement(UPDATE_MATCH)) {
            pstm.setDate(1, match.getDate());
            pstm.setInt(2, match.getHomeGoals());
            pstm.setInt(3, match.getGuestGoals());
            pstm.setInt(4, match.getIdMatch());
            pstm.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error("Cannot update match ", ex);
            throw ex;
        }
        return match.getIdMatch();
    }

    @Override
    public int deleteMatch(Connection conn, int idMatch) throws SQLException {
        try (PreparedStatement pstm = conn.prepareStatement(DELETE_MATCH)) {
            pstm.setInt(1, idMatch);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error("Cannot delete match ", ex);
            throw ex;
        }
        return idMatch;
    }

    @Override
    public int insertMatchPlayer(Connection conn, MatchPlayer matchPlayer) throws SQLException {
        try (PreparedStatement pstm = conn.prepareStatement(ADD_PLAYER_TO_MATCH)) {
            pstm.setInt(1, matchPlayer.getIdMatch());
            pstm.setInt(2, matchPlayer.getIdPlayer());
            pstm.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error("Cannot add player to match ", ex);
            throw ex;
        }
        return matchPlayer.getIdPlayer();
    }

    @Override
    public Championship findChampionship(Connection conn, int idChampionship) throws SQLException {
        Championship result = null;
        try (PreparedStatement pstm = conn.prepareStatement(FIND_CHAMPIONSHIP_BY_ID)) {
            pstm.setInt(1, idChampionship);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                result = new Championship();
                result.setIdChampionship(rs.getInt(1));
                result.setName(rs.getString(2));
                result.setDescription(rs.getString(3));
            }
        } catch (SQLException ex) {
            LOGGER.error("Cannot find championship ", ex);
            throw ex;
        }
        return result;
    }

    @Override
    public Season findSeason(Connection conn, int idSeason) throws SQLException {
        Season result = null;
        try (PreparedStatement pstm = conn.prepareStatement(FIND_SEASON_BY_ID)) {
            pstm.setInt(1, idSeason);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                result = new Season();
                result.setIdSeason(rs.getInt(1));
                result.setName(rs.getString(2));
                result.setStart(rs.getDate(3));
                result.setEnd(rs.getDate(4));
            }
        } catch (SQLException ex) {
            LOGGER.error("Cannot find season ", ex);
            throw ex;
        }
        return result;
    }

    @Override
    public Team findTeam(Connection conn, int idTeam) throws SQLException {
        Team result = new Team();
        try (PreparedStatement pstm = conn.prepareStatement(FIND_TEAM_BY_ID)) {
            pstm.setInt(1, idTeam);
            ResultSet rs = pstm.executeQuery();
            if (rs.next()) {
                result.setIdTeam(rs.getInt(1));
                result.setName(rs.getString(2));
            }
        } catch (SQLException ex) {
            LOGGER.error("Cannot find team ", ex);
            throw ex;
        }
        return result;
    }

    @Override
    public Match findMatch(Connection conn, int idMatch) throws SQLException {
        Match match = null;
        try (PreparedStatement pstm = conn.prepareStatement(FIND_MATCH)) {
            pstm.setInt(1, idMatch);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                match = new Match();
                match.setIdMatch(rs.getInt(1));
                match.setIdHomeTeam(rs.getInt(2));
                match.setIdGuestTeam(rs.getInt(3));
                match.setIdChampionship(rs.getInt(4));
                match.setIdSeason(rs.getInt(5));
                match.setDate(rs.getDate(6));
                match.setHomeGoals(rs.getInt(7));
                match.setGuestGoals(rs.getInt(8));
            }
        }
        return match;
    }

    @Override
    public int replacePlayer(Connection conn, int idMatch, int idPlayer, int idReplacer) throws SQLException {
        try (PreparedStatement pstm = conn.prepareStatement(REPLACE_PLAYER_AT_MATCH)) {
            pstm.setInt(1, idReplacer);
            pstm.setInt(2, idMatch);
            pstm.setInt(3, idPlayer);
            pstm.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error("Cannot replace player to match ", ex);
            throw ex;
        }
        return idReplacer;
    }

}