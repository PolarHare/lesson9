package com.polarnick.day09.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Date: 18.11.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
@DatabaseTable(tableName = "forecastsData")
public class ForecastData {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private ForecastForCity forecastForCity;

    @DatabaseField()
    private String summary;
    @DatabaseField()
    private String iconType;
    @DatabaseField()
    private long time;

    @DatabaseField()
    private int temperature;
    @DatabaseField()
    private int minTemperature;
    @DatabaseField()
    private int maxTemperature;

    @DatabaseField()
    private int precipProbability;
    @DatabaseField()
    private double precipIntensity;
    @DatabaseField()
    private String precipType;

    @DatabaseField()
    private int windSpeed;
    @DatabaseField()
    private int windBearing;

    public ForecastData() {
    }
}
