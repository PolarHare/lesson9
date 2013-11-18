package com.polarnick.day09.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.polarnick.day09.entities.City;

import java.sql.SQLException;
import java.util.List;

/**
 * Date: 18.11.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
public class CityDAO extends BaseDaoImpl<City, Integer> {

    protected CityDAO(ConnectionSource connectionSource, Class<City> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<City> getAllCities() throws SQLException {
        return this.queryForAll();
    }
}