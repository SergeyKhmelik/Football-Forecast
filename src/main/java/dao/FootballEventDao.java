package dao;

import entity.db.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface FootballEventDao {

    List<Championship> findChampionships(Connection conn) throws SQLException;

    List<Season> findSeasons(Connection conn) throws SQLException;

    List<Match> findMatches(Connection conn, int idChampionship, int idSeason) throws SQLException;

    List<Player> findPlayers(Connection conn, int idTeam) throws SQLException;

    List<Player> findAddedPlayers(Connection conn, int idTeam, int idMatch) throws SQLException;

    List<Player> findNotAddedPlayers(Connection conn, int idTeam, int idMatch) throws SQLException;

    List<Team> findTeams(Connection conn) throws SQLException;

    int insertMatch(Connection conn, Match match) throws SQLException;

    int updateMatch(Connection conn, Match match) throws SQLException;

    int deleteMatch(Connection conn, int idMatch) throws SQLException;

    int insertMatchPlayer(Connection conn, MatchPlayer matchPlayer) throws SQLException;

    Championship findChampionship(Connection conn, int idChampionship) throws SQLException;

    Season findSeason(Connection conn, int idSeason) throws SQLException;

    Team findTeam(Connection conn, int idTeam) throws SQLException;

    Match findMatch(Connection conn, int idMatch) throws SQLException;

    int replacePlayer(Connection conn, int idMatch, int idPlayer, int idReplacer) throws SQLException;
}