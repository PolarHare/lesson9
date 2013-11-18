package com.polarnick.day09.weather;

import android.util.Log;
import com.forecastiolib.FIOCurrently;
import com.forecastiolib.FIODaily;
import com.forecastiolib.FIOHourly;
import com.forecastiolib.ForecastIO;
import com.polarnick.day09.dao.DatabaseHelperFactory;
import com.polarnick.day09.entities.City;
import com.polarnick.day09.entities.ForecastData;
import com.polarnick.day09.entities.ForecastForCity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 18.11.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
public class WeatherProvider {
    private static final String API_KEY = "a4e9018570320442da83bf48ade940d8";
    private static final ForecastIO FIO = new ForecastIO(API_KEY);

    static {
        FIO.setUnits(ForecastIO.UNITS_SI);
        FIO.setExcludeURL("minutely");
    }

    public static synchronized ForecastForCity getForecastForCity(City city) {
        ForecastForCity newForecastForCity = new ForecastForCity();
        newForecastForCity.setCity(city);
        newForecastForCity.setDownloadedAt(System.currentTimeMillis());

        FIO.getForecast(city.getLatitude(), city.getLongitude());
        newForecastForCity.setCurrent(new ForecastData(new FIOCurrently(FIO).get()));

        final FIODaily fioDaily = new FIODaily(FIO);
        newForecastForCity.setDaysSummary(fioDaily.getDaily().summary());
        List<ForecastData> days = new ArrayList<ForecastData>(ForecastForCity.DAYS_COUNT);
        for (int i = 0; i < ForecastForCity.DAYS_COUNT; i++) {
            days.add(new ForecastData(fioDaily.getDay(i)));
        }
        newForecastForCity.setDays(days);

        final FIOHourly fioHourly = new FIOHourly(FIO);
        newForecastForCity.setHoursSummary(fioHourly.getHourly().summary());
        List<ForecastData> hours = new ArrayList<ForecastData>(ForecastForCity.HOURS_COUNT);
        for (int i = 0; i < ForecastForCity.HOURS_COUNT; i++) {
            ForecastData[] hoursData = new ForecastData[ForecastForCity.HOURS_END_OFFSET[i] - ForecastForCity.HOURS_START_OFFSET[i]];
            for (int j = ForecastForCity.HOURS_START_OFFSET[i]; j < ForecastForCity.HOURS_END_OFFSET[i]; j++) {
                hoursData[j - ForecastForCity.HOURS_START_OFFSET[i]] = new ForecastData(fioHourly.getHour(j));
            }
            hours.add(new ForecastData(hoursData));
        }
        return newForecastForCity;
    }

}
