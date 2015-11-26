package service.mysql;

import dao.FootballEventDao;
import dao.UserDao;
import entity.client.MatchInfo;
import entity.db.*;
import org.apache.log4j.Logger;
import service.OverviewService;
import util.Operation;
import util.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MysqlOverviewService implements OverviewService {

    private static final Logger LOGGER = Logger
            .getLogger(MysqlOverviewService.class);

    private TransactionManager transactionManager;
    private FootballEventDao footballEventDao;
    private UserDao userDao;

    public MysqlOverviewService(TransactionManager transactionManager,
                                FootballEventDao footballEventDao, UserDao userDao) {
        this.transactionManager = transactionManager;
        this.footballEventDao = footballEventDao;
        this.userDao = userDao;
    }

    @Override
    public List<User> findAllUsers() throws SQLException {
        return transactionManager.doTransaction(TransactionManager.USERS_DB,
                new Operation<List<User>>() {

                    @Override
                    public List<User> execute(Connection conn) throws SQLException {
                        return userDao.findUsers(conn);
                    }

                }
        );
    }

    @Override
    public List<Championship> findChampionships() throws SQLException {
        return transactionManager.doTransaction(TransactionManager.FOOTBALL_DB,
                new Operation<List<Championship>>() {

                    @Override
                    public List<Championship> execute(Connection conn) throws SQLException {
                        return footballEventDao.findChampionships(conn);
                    }

                });
    }

    @Override
    public List<Season> findSeasons() throws SQLException {
        return transactionManager.doTransaction(TransactionManager.FOOTBALL_DB,
                new Operation<List<Season>>() {

                    @Override
                    public List<Season> execute(Connection conn) throws SQLException {
                        return footballEventDao.findSeasons(conn);
                    }

                });
    }

    @Override
    public List<Team> findTeams() throws SQLException {
        return transactionManager.doTransaction(TransactionManager.FOOTBALL_DB, new Operation<List<Team>>() {
            @Override
            public List<Team> execute(Connection conn) throws SQLException {
                List<Team> result = new ArrayList<Team>();
                result = footballEventDao.findTeams(conn);
                return result;
            }
        });
    }

    @Override
    public MatchInfo findMatch(final int idMatch) throws SQLException {
        return transactionManager.doTransaction(TransactionManager.FOOTBALL_DB, new Operation<MatchInfo>() {
            @Override
            public MatchInfo execute(Connection conn) throws SQLException {
                MatchInfo result = new MatchInfo();
                Match match = footballEventDao.findMatch(conn, idMatch);
                result.setIdMatch(match.getIdMatch());
                result.setDate(match.getDate());
                result.setHomeTeam(footballEventDao.findTeam(conn, match.getIdHomeTeam()));
                result.setHomeGoals(match.getHomeGoals());
                result.setGuestGoals(match.getGuestGoals());
                result.setGuestTeam(footballEventDao.findTeam(conn, match.getIdGuestTeam()));
                return result;
            }
        });
    }

    @Override
    public List<MatchInfo> findMatches(final int idChampionship, final int idSeason) throws SQLException {
        return transactionManager.doTransaction(TransactionManager.FOOTBALL_DB, new Operation<List<MatchInfo>>() {
            @Override
            public List<MatchInfo> execute(Connection conn) throws SQLException {
                List<MatchInfo> result = new ArrayList<MatchInfo>();
                List<Match> matches = footballEventDao.findMatches(conn, idChampionship, idSeason);
                for (Match match : matches) {
                    MatchInfo currentMatchInfo = new MatchInfo();
                    currentMatchInfo.setIdMatch(match.getIdMatch());
                    currentMatchInfo.setDate(match.getDate());
                    currentMatchInfo.setHomeTeam(footballEventDao.findTeam(conn, match.getIdHomeTeam()));
                    currentMatchInfo.setHomeGoals(match.getHomeGoals());
                    currentMatchInfo.setGuestGoals(match.getGuestGoals());
                    currentMatchInfo.setGuestTeam(footballEventDao.findTeam(conn, match.getIdGuestTeam()));
                    result.add(currentMatchInfo);
                }
                return result;
            }
        });
    }

    @Override
    public int updateMatch(final Match match) throws SQLException {
        return transactionManager.doTransaction(TransactionManager.FOOTBALL_DB, new Operation<Integer>() {
            @Override
            public Integer execute(Connection conn) throws SQLException {
                return footballEventDao.updateMatch(conn, match);
            }
        });
    }

    @Override
    public int addMatch(final Match match) throws SQLException {
        return transactionManager.doTransaction(TransactionManager.FOOTBALL_DB, new Operation<Integer>() {
            @Override
            public Integer execute(Connection conn) throws SQLException {
                return footballEventDao.insertMatch(conn, match);
            }
        });
    }

    @Override
    public int deleteMatch(final int idMatch) throws SQLException {
        return transactionManager.doTransaction(TransactionManager.FOOTBALL_DB, new Operation<Integer>() {
            @Override
            public Integer execute(Connection conn) throws SQLException {
                return footballEventDao.deleteMatch(conn, idMatch);
            }
        });
    }

    @Override
    public List<Player> findAddedPlayers(final int idMatch, final int idTeam) throws SQLException {
       return transactionManager.doTransaction(TransactionManager.FOOTBALL_DB, new Operation<List<Player>>() {
           @Override
           public List<Player> execute(Connection conn) throws SQLException {
               return footballEventDao.findAddedPlayers(conn, idTeam, idMatch);
           }
       });
    }

    @Override
    public List<Player> findNotAddedPlayers(final int idMatch, final int idTeam) throws SQLException {
        return transactionManager.doTransaction(TransactionManager.FOOTBALL_DB, new Operation<List<Player>>() {
            @Override
            public List<Player> execute(Connection conn) throws SQLException {
                return footballEventDao.findNotAddedPlayers(conn, idTeam, idMatch);
            }
        });
    }

    @Override
    public int addPlayers(final List<MatchPlayer> players) throws SQLException {
        return transactionManager.doTransaction(TransactionManager.FOOTBALL_DB, new Operation<Integer>() {
            @Override
            public Integer execute(Connection conn) throws SQLException {
                int result = 0;
                for (MatchPlayer matchPlayer : players) {
                    result = footballEventDao.insertMatchPlayer(conn, matchPlayer);
                }
                return result;
            }
        });
    }

    @Override
    public Championship findChampionship(final int idChampionship) throws SQLException {
        return transactionManager.doTransaction(TransactionManager.FOOTBALL_DB, new Operation<Championship>() {
            @Override
            public Championship execute(Connection conn) throws SQLException {
                return footballEventDao.findChampionship(conn, idChampionship);
            }
        });
    }

    @Override
    public Season findSeason(final int idSeason) throws SQLException {
        return transactionManager.doTransaction(TransactionManager.FOOTBALL_DB, new Operation<Season>() {
            @Override
            public Season execute(Connection conn) throws SQLException {
                return footballEventDao.findSeason(conn, idSeason);
            }
        });
    }

    @Override
    public int replacePlayer(final int idMatch, final int idPlayer, final int idReplacer) throws SQLException {
        return transactionManager.doTransaction(TransactionManager.FOOTBALL_DB, new Operation<Integer>() {
            @Override
            public Integer execute(Connection conn) throws SQLException {
                return footballEventDao.replacePlayer(conn, idMatch, idPlayer, idReplacer);
            }
        });
    }

}
