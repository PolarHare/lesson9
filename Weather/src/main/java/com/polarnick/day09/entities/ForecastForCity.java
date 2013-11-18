package com.polarnick.day09.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

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
    private long downloadedAt;
    @DatabaseField()
    private ForecastData current;
    @DatabaseField()
    private String hoursSummary;
    @ForeignCollectionField(eager = true)
    private List<ForecastData> hours;
    @DatabaseField()
    private String daysSummary;
    @ForeignCollectionField(eager = true)
    private List<ForecastData> days;

    public ForecastForCity() {
    }

    public ForecastForCity(City city, long downloadedAt, ForecastData current, String hoursSummary, List<ForecastData> hours, String daysSummary, List<ForecastData> days) {
        this.city = city;
        this.downloadedAt = downloadedAt;
        this.current = current;
        this.hoursSummary = hoursSummary;
        this.hours = hours;
        this.daysSummary = daysSummary;
        this.days = days;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public long getDownloadedAt() {
        return downloadedAt;
    }

    public void setDownloadedAt(long downloadedAt) {
        this.downloadedAt = downloadedAt;
    }

    public ForecastData getCurrent() {
        return current;
    }

    public void setCurrent(ForecastData current) {
        this.current = current;
    }

    public String getHoursSummary() {
        return hoursSummary;
    }

    public void setHoursSummary(String hoursSummary) {
        this.hoursSummary = hoursSummary;
    }

    public List<ForecastData> getHours() {
        return hours;
    }

    public void setHours(List<ForecastData> hours) {
        this.hours = hours;
    }

    public String getDaysSummary() {
        return daysSummary;
    }

    public void setDaysSummary(String daysSummary) {
        this.daysSummary = daysSummary;
    }

    public List<ForecastData> getDays() {
        return days;
    }

    public void setDays(List<ForecastData> days) {
        this.days = days;
    }
}
