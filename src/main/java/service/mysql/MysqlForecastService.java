package service.mysql;

import dao.ForecastDao;
import entity.client.MatchInfo;
import entity.client.Prediction;
import entity.db.Player;
import service.ForecastService;
import service.OverviewService;
import util.Operation;
import util.TransactionManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class MysqlForecastService implements ForecastService {

    private TransactionManager transactionManager;
    private ForecastDao forecastDao;
    private OverviewService overviewService;

    public MysqlForecastService(TransactionManager transactionManager, ForecastDao forecastDao, OverviewService overviewService) {
        this.transactionManager = transactionManager;
        this.forecastDao = forecastDao;
        this.overviewService = overviewService;
    }


    @Override
    public Prediction makePrediction(final int idMatch) throws SQLException {
        return transactionManager.doTransaction(TransactionManager.FOOTBALL_DB, new Operation<Prediction>() {
            @Override
            public Prediction execute(Connection conn) throws SQLException {
                Prediction result = new Prediction();

                MatchInfo match = overviewService.findMatch(idMatch);
                match.setHomeTeamPlayers(overviewService.findAddedPlayers(match.getIdMatch(), match.getHomeTeam().getIdTeam()));
                match.setGuestTeamPlayers(overviewService.findAddedPlayers(match.getIdMatch(), match.getGuestTeam().getIdTeam()));
                result.setMatchInfo(match);
                result.setHomeTeamChance(getTeamPredictionRate(conn, match));
                result.setGuestTeamChance(1 - result.getHomeTeamChance());

                return result;
            }
        });
    }

    private double getTeamPredictionRate(Connection conn, MatchInfo matchInfo) throws SQLException {
        double result =
                        0.42 * getX1(matchInfo) +
                        0.25 * getX2(conn, matchInfo) +
                        0.1 * getX3(conn, matchInfo) +
                        0.23 * getX4(conn, matchInfo);
        System.out.println("X1 = " + getX1(matchInfo));
        System.out.println("X2 = " + getX2(conn, matchInfo));
        System.out.println("X3 = " + getX3(conn, matchInfo));
        System.out.println("X4 = " + getX4(conn, matchInfo));
        return result;
    }


    private double getX1(MatchInfo matchInfo) {
        double X1;
        double A1 = getAvgPlayersRating(matchInfo.getHomeTeamPlayers());
        double B1 = getAvgPlayersRating(matchInfo.getGuestTeamPlayers());

        //Coerce average values
        double deduction = 0;
        double coeff = 5.0;
        if(A1 < B1){
            deduction = A1 - coeff;
        } else if(B1 < A1) {
            deduction = B1 - coeff;
        }

        A1 = A1 - deduction;
        B1 = B1 - deduction;
        X1 = A1 / (A1 + B1);
        return X1;
    }

    private double getX2(Connection conn, MatchInfo matchInfo) throws SQLException {
        double X2;
        double A2 = getTeamPerformance(
                forecastDao.getMatchesByTeamInCurrentSeason(conn, matchInfo.getHomeTeam().getIdTeam(), matchInfo.getDate()));
        double B2 = getTeamPerformance(
                forecastDao.getMatchesByTeamInCurrentSeason(conn, matchInfo.getGuestTeam().getIdTeam(), matchInfo.getDate()));
        X2 = A2 / (A2 + B2);
        return X2;
    }

    private double getX3(Connection conn, MatchInfo matchInfo) throws SQLException {
        double X3;
        double A3 = getTeamPerformance(
                forecastDao.getMatchesByTeamAgainstOpponent(conn, matchInfo.getHomeTeam().getIdTeam(), matchInfo.getGuestTeam().getIdTeam(), matchInfo.getDate()));
        double B3 = getTeamPerformance(
                forecastDao.getMatchesByTeamAgainstOpponent(conn, matchInfo.getGuestTeam().getIdTeam(), matchInfo.getHomeTeam().getIdTeam(), matchInfo.getDate()));
        X3 = A3 / (A3 + B3);
        return X3;
    }

    private double getX4(Connection conn, MatchInfo matchInfo) throws SQLException {
        double X4;
        double A4 = countHomeFieldFactor(conn, matchInfo.getHomeTeam().getIdTeam(), matchInfo.getDate());
        double B4 = countGuestFieldFactor(conn, matchInfo.getGuestTeam().getIdTeam(), matchInfo.getDate());

        X4 = A4 / (A4 + B4);
        return X4;
    }


    private double getAvgPlayersRating(List<Player> teamPlayers) {
        double A1 = 0;
        for (Player player : teamPlayers) {
            A1 += player.getRank();
        }
        A1 = A1 / 11;
        return A1;
    }

    private double getTeamPerformance1(List<Integer> scores) {
        double A2 = 0;
        //Get C
        double C;
        double cDivider = 0;
        for (int i = 0; i < scores.size(); i++) {
            cDivider += Math.pow(2, i);
        }

        C = 1 / cDivider;
        //Multiply importance to result and ADD TO SUMM(!)
        for (int i = 0; i < scores.size(); i++) {
            //Coercing score
            double coercedScore;
            if(scores.get(i) > 5){
                coercedScore = 1;
            } else if(scores.get(i) < -5) {
                coercedScore = 0;
            } else {
                coercedScore = 0.5 + 0.1*scores.get(i);
            }

//            A2 += Math.pow(2, i) * scores.get(i) * C;
            A2 += Math.pow(2, i) * coercedScore / cDivider;
        }
        System.out.println("A2 " + A2);
        return A2;
    }

    private double countHomeFieldFactor(Connection conn, int idTeam, Date date) throws SQLException {
        double result;
        List<Integer> matches = forecastDao.getHomeMatchesByTeam(conn, idTeam, date);
        double totalScore = 0;
        for (Integer i : matches) {
            if (i == 0) {
                totalScore += 1;
            } else if (i > 0) {
                totalScore += 3;
            }
        }
        result = totalScore / (matches.size() * 3);
        return result;
    }

    private double countGuestFieldFactor(Connection conn, int idTeam, Date date) throws SQLException {
        double result;
        List<Integer> matches = forecastDao.getGuestMatchesByTeam(conn, idTeam, date);
        double totalScore = 0;
        for (Integer i : matches) {
            if (i == 0) {
                totalScore += 1;
            } else if (i > 0) {
                totalScore += 3;
            }
        }
        result = totalScore / (matches.size() * 3);
        return result;
    }

    private double getTeamPerformance(List<Integer> scores) {
        double A2 = 0;
        //Get C
        double C;
        double cDivider = 0;
        for (int i = 0; i < scores.size(); i++) {
            cDivider += Math.pow(2, i);
        }

        C = 1 / cDivider;
        //Multiply importance to result and ADD TO SUMM(!)
        for (int i = 0; i < scores.size(); i++) {
            //Coercing score
            double coercedScore;
            if(scores.get(i) > 5){
                coercedScore = 1;
            } else if(scores.get(i) < -5) {
                coercedScore = 0;
            } else {
                coercedScore = 0.5 + 0.1*scores.get(i);
            }

//            A2 += Math.pow(2, i) * scores.get(i) * C;
            A2 += Math.pow(2, i) * coercedScore / cDivider;
        }


        BigDecimal A2big = BigDecimal.ZERO;

        BigDecimal Cbig;
        BigDecimal cDivBig = BigDecimal.ZERO;

        for(int i = 0; i < scores.size(); i++){
            cDivBig = cDivBig.add(BigDecimal.valueOf(Math.pow(2, i)));
        }

        Cbig = BigDecimal.ONE.divide(cDivBig, 20 ,BigDecimal.ROUND_HALF_UP);

        for (int i = 0; i < scores.size(); i++) {
            //Coercing score
            double coercedScore;
            if(scores.get(i) > 5){
                coercedScore = 1;
            } else if(scores.get(i) < -5) {
                coercedScore = 0;
            } else {
                coercedScore = 0.5 + 0.1*scores.get(i);
            }

            A2big = A2big.add(BigDecimal.valueOf(Math.pow(2,i)).multiply(BigDecimal.valueOf(coercedScore)).multiply(Cbig));
        }
       return A2big.doubleValue();
    }

}
