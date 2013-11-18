package com.polarnick.day09.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.List;

/**
 * Date: 18.11.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
@DatabaseTable(tableName = "forecastsForCity")
public class ForecastForCity {

    public static final int HOURS_COUNT = 3;
    public static final int[] HOURS_START_OFFSET = new int[]{0, 1, 3};
    public static final int[] HOURS_END_OFFSET = new int[]{1, 3, 7};

    public static final int DAYS_COUNT = 7;

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(foreign = true)
    private City city;

    @DatabaseField()
    private Date downloadedAt;
    @DatabaseField()
    private ForecastData current;
    @DatabaseField()
    private String hoursSummary;
    @ForeignCollectionField(eager = true)
    private List<ForecastData> hours;
    @DatabaseField()
    private String gaysSummary;
    @ForeignCollectionField(eager = true)
    private List<ForecastData> days;

    public ForecastForCity() {
    }
}
