package com.polarnick.day09.cities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.polarnick.day09.R;
import com.polarnick.day09.dao.DatabaseHelperFactory;
import com.polarnick.day09.entities.City;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 18.11.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
public class CitiesManagementActivity extends Activity {
    public static final String CITIES_LIST_EXTRA = "cities list";
    public static final int CHOOSE_CITY_REQUEST = 239;

    ArrayAdapter<City> citiesListAdapter;
    List<City> citiesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cities_managment);

        ListView citiesListView = (ListView) findViewById(R.id.listOfCities);
        DatabaseHelperFactory.setHelper(getApplicationContext());//TODO: to delete
        citiesList = DatabaseHelperFactory.getHelper().getCityDAO().getAllCities();
        citiesListAdapter = new CityListAdapter(this, citiesList);
        citiesListView.setAdapter(citiesListAdapter);

        EditText cityToAdd = (EditText) findViewById(R.id.nameOfCityToAdd);
        ImageButton addNew = (ImageButton) findViewById(R.id.addCityButton);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAndAddCity();
            }
        });
    }

    private void addCity(City city) {
        citiesList.add(city);
        try {
            DatabaseHelperFactory.getHelper().getCityDAO().create(city);
        } catch (SQLException e) {
            Log.e(CitiesManagementActivity.class.getName(), "Saving city with name=" + city.getName() + " was failed!");
            throw new RuntimeException(e);
        }
        citiesListAdapter.notifyDataSetChanged();
    }

    private void askUserToChooseCityFromList(ArrayList<City> cities) {
        Intent intent = new Intent(this, CityChoiseActivity.class);
        intent.putExtra(CITIES_LIST_EXTRA, cities);
        startActivityForResult(intent, CHOOSE_CITY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            addCity((City) data.getExtras().get(CityChoiseActivity.SELECTED_CITY));
        }
    }

    private void searchAndAddCity() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getResources().getString(R.string.SEARCHING_CITY));
        progressDialog.setCancelable(false);
        progressDialog.show();

        EditText cityName = (EditText) findViewById(R.id.nameOfCityToAdd);
        new AsyncTask<String, Void, Void>() {

            @Override
            protected Void doInBackground(String... params) {
                String cityName = params[0];
                final ArrayList<City> cities = CitiesProvider.getCities(cityName);
                if (cities.size() == 0) {
                    progressDialog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CitiesManagementActivity.this, R.string.NO_CITY_FOUND, Toast.LENGTH_LONG).show();
                        }
                    });
                } else if (cities.size() == 1) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            addCity(cities.get(0));
                        }
                    });
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    askUserToChooseCityFromList(cities);
                }
                return null;
            }

        }.execute(cityName.getText().toString());
    }
}
