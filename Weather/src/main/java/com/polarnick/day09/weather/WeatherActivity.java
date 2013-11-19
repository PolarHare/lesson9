package com.polarnick.day09.weather;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.polarnick.day09.R;
import com.polarnick.day09.Utils;
import com.polarnick.day09.cities.CitiesManagementActivity;
import com.polarnick.day09.cities.SimpleCityListAdapter;
import com.polarnick.day09.dao.DatabaseHelperFactory;
import com.polarnick.day09.entities.City;
import com.polarnick.day09.entities.ForecastData;
import com.polarnick.day09.entities.ForecastForCity;

import java.sql.SQLException;
import java.util.List;

/**
 * Date: 17.11.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
public class WeatherActivity extends Activity {

    private List<City> cities;
    private City selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecast_main);

        ImageButton configureCities = (ImageButton) findViewById(R.id.configureCities);
        configureCities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WeatherActivity.this, CitiesManagementActivity.class));
            }
        });

        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                showForecastForCity(selectedCity);
            }
        }, new IntentFilter(WeatherUpdaterService.FORECAST_UPDATE_TAG));
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseHelperFactory.setHelper(getApplicationContext());
        loadCities();
        loadCitiesToSpinner();
        startWeatherUpdaterService();
    }

    private void startWeatherUpdaterService() {
        Intent intent = new Intent(WeatherActivity.this, WeatherUpdaterService.class);
        startService(intent);
    }

    private void loadCities() {
        cities = DatabaseHelperFactory.getHelper().getCityDAO().getAllCities();
    }

    private void loadCitiesToSpinner() {
        Spinner citiesSpinner = (Spinner) findViewById(R.id.citiesSpinner);
        final SimpleCityListAdapter adapter = new SimpleCityListAdapter(this, cities, 16);
        citiesSpinner.setAdapter(adapter);
        citiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = cities.get(position);
                showForecastForCity(selectedCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        citiesSpinner.setEnabled(cities.size() > 0);
        if (cities.size() == 0) {
            selectedCity = null;
            showForecastForCity(selectedCity);
        }
    }

    private void showForecastForCity(City city) {
        try {
            if (city == null) {
                showMessage("Please add city!");
                return;
            }
            DatabaseHelperFactory.getHelper().getCityDAO().refresh(city);
            ForecastForCity forecast = city.getForecast();
            if (forecast == null) {
                if (Utils.isOnline(this)) {
                    showRefreshingProgress("Forecast is loading...");
                } else {
                    showMessage("There are no internet and no forecast was downloaded in past!\nPlease enable internet.");
                }
                return;
            }
            DatabaseHelperFactory.getHelper().getForecastForCityDAO().refresh(forecast);
            DatabaseHelperFactory.getHelper().getForecastDataDAO().refresh(forecast.getCurrent());
            showForecast(forecast);
        } catch (SQLException e) {
            Log.e(WeatherActivity.class.getName(), "Updating city with name=" + city.getName() + " and id=" + city.getId() + "!");
            throw new RuntimeException(e);
        }
    }

    private void showForecast(ForecastForCity forecast) {
        LinearLayout layoutForForecast = (LinearLayout) findViewById(R.id.forecastLayout);
        layoutForForecast.removeAllViews();

        final TodayView todayView = new TodayView(this, forecast.getCurrent());
        layoutForForecast.addView(todayView);
        todayView.init();
        Utils.addDivider(this, layoutForForecast);

        TextView hoursSummary = new TextView(this);
        hoursSummary.setText(forecast.getHoursSummary());
        layoutForForecast.addView(hoursSummary);
        Utils.addDivider(this, layoutForForecast);

        List<ForecastData> hours = forecast.getHours();
        for (int i = 0; i < hours.size(); i++) {
            ForecastData data = hours.get(i);
            final HourView hour = new HourView(this, ForecastForCity.HOURS_DIFF[i], data);
            layoutForForecast.addView(hour);
            hour.init();
            Utils.addDivider(this, layoutForForecast);
        }

        TextView daysSummary = new TextView(this);
        daysSummary.setText(forecast.getDaysSummary());
        layoutForForecast.addView(daysSummary);

        for (ForecastData data : forecast.getDays()) {
            Utils.addDivider(this, layoutForForecast);
            final TodayView day = new TodayView(this, data);
            layoutForForecast.addView(day);
            day.init();
        }
    }

    private void showRefreshingProgress(String message) {
        LinearLayout layoutForForecast = (LinearLayout) findViewById(R.id.forecastLayout);
        layoutForForecast.removeAllViews();

        final TextView messageView = new TextView(this);
        messageView.setText(message);
        messageView.setTextSize(getResources().getDimension(R.dimen.labelsTextSize));
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 0, 1);
        params.gravity = Gravity.CENTER;
        messageView.setLayoutParams(params);
        messageView.setGravity(Gravity.BOTTOM);
        layoutForForecast.addView(messageView);

        final ProgressBar progressBar = new ProgressBar(this);

        LinearLayout layout2 = new LinearLayout(this);
        layout2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout2.setGravity(Gravity.CENTER_HORIZONTAL);
        layout2.addView(progressBar);

        LinearLayout layout1 = new LinearLayout(this);
        layout1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        layout1.addView(layout2);

        layoutForForecast.addView(layout1);
    }

    private void showMessage(String message) {
        LinearLayout layoutForForecast = (LinearLayout) findViewById(R.id.forecastLayout);
        layoutForForecast.removeAllViews();

        final TextView messageView = new TextView(this);
        messageView.setText(message);
        messageView.setTextSize(getResources().getDimension(R.dimen.labelsTextSize));
        messageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        messageView.setGravity(Gravity.CENTER);
        layoutForForecast.addView(messageView);
    }
}
