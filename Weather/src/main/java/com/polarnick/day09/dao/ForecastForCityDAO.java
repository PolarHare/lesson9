package com.polarnick.day09.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.polarnick.day09.entities.ForecastForCity;

import java.sql.SQLException;
import java.util.List;

/**
 * Date: 18.11.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
public class ForecastForCityDAO extends BaseDaoImpl<ForecastForCity, Integer> {

    protected ForecastForCityDAO(ConnectionSource connectionSource, Class<ForecastForCity> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<ForecastForCity> getAllCities() throws SQLException {
        return this.queryForAll();
    }
}