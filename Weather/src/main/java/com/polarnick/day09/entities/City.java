package com.polarnick.day09.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Date: 18.11.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
@DatabaseTable(tableName = "cities")
public class City {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private ForecastForCity forecast;

    @DatabaseField()
    private String name;
    @DatabaseField()
    private String latitude;
    @DatabaseField()
    private String longitude;

    public City() {
    }
}
