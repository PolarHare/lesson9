package com.polarnick.day09;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.eclipsesource.json.*;
import com.forecastiolib.*;
import com.polarnick.day09.dao.DatabaseHelperFactory;

import java.util.Date;

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
//                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                List<String> matchingProviders = locationManager.getAllProviders();
//                for (String provider : matchingProviders) {
//                    Location location = locationManager.getLastKnownLocation(provider);
//                    if (location != null) {
//                        float accuracy = location.getAccuracy();
//                        long time = location.getTime();
//                    }
//                }

                final Geocoder geocoder = new Geocoder();
                GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress("Петербург").getGeocoderRequest();
                GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);


                new JsonArray();
                final ForecastIO fio = new ForecastIO("a4e9018570320442da83bf48ade940d8");
                fio.setUnits(ForecastIO.UNITS_AUTO);
                fio.getForecast(geocoderResponse.getResults().get(0).getGeometry().getLocation().getLat().toString(),
                        geocoderResponse.getResults().get(0).getGeometry().getLocation().getLng().toString());

                FIODaily daily = new FIODaily(fio);

                //In case there is no daily data available
                if (daily.days() < 0)
                    System.out.println("No daily data.");
                else
                    System.out.println("\nDaily:\n");
                //Print daily data
                for (int i = 0; i < daily.days(); i++) {
                    String[] h = daily.getDay(i).getFieldsArray();
                    System.out.println("Day #" + (i + 1));
                    for (int j = 0; j < h.length; j++)
                        System.out.println(h[j] + ": " + daily.getDay(i).getByKey(h[j]));
                    System.out.println("\n");
                }
                return null;
            }
        }.execute();
    }
}
