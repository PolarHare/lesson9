package com.polarnick.day09.weather;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import com.polarnick.day09.cities.CitiesProvider;
import com.polarnick.day09.dao.DatabaseHelperFactory;
import com.polarnick.day09.entities.City;
import com.polarnick.day09.entities.ForecastForCity;

import java.util.List;

/**
 * Date: 17.11.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
public class WeatherActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseHelperFactory.setHelper(getApplicationContext());
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                List<City> cities = CitiesProvider.getCities("Петербург");
                for (City city : cities) {
                    ForecastForCity forCity = WeatherProvider.getForecastForCity(city);
                    int a = 239;
                }
                return null;
            }
        }.execute();
    }
}
