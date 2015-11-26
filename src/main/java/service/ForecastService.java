package service;

import entity.client.Prediction;

import java.sql.SQLException;

public interface ForecastService {

    Prediction makePrediction(int idMatch) throws SQLException;

}
