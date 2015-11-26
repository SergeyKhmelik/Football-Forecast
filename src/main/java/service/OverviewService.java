package service;

import entity.client.MatchInfo;
import entity.db.*;

import java.sql.SQLException;
import java.util.List;

public interface OverviewService {

    List<User> findAllUsers() throws SQLException;

    List<Championship> findChampionships() throws SQLException;

    List<Season> findSeasons() throws SQLException;

    List<Team> findTeams() throws SQLException;

    MatchInfo findMatch(int idMatch) throws SQLException;

    List<MatchInfo> findMatches(int idChampionship, int idSeason) throws SQLException;

    int updateMatch(Match match) throws SQLException;

    int addMatch(Match match) throws SQLException;

    int deleteMatch(int idMatch) throws SQLException;

    List<Player> findAddedPlayers(int idMatch, int idTeam) throws SQLException;

    List<Player> findNotAddedPlayers(int idMatch, int idTeam) throws SQLException;

    int addPlayers(List<MatchPlayer> players) throws SQLException;

    Championship findChampionship(int idChampionship) throws SQLException;

    Season findSeason(int idSeason) throws SQLException;

    int replacePlayer(int idMatch, int idPlayer, int idReplacer) throws SQLException;
}
