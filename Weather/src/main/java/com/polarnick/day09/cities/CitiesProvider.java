package com.polarnick.day09.cities;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.polarnick.day09.entities.City;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 18.11.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
public class CitiesProvider {

    private static final Geocoder geocoder = new Geocoder();

    public static synchronized List<City> getCities(String address) {
        List<City> cities = new ArrayList<City>();
        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address).getGeocoderRequest();
        GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
        for (GeocoderResult geocoderResult : geocoderResponse.getResults()) {
            City city = new City();
            city.setName(geocoderResult.getFormattedAddress());
            city.setLatitude(geocoderResult.getGeometry().getLocation().getLat().toString());
            city.setLongitude(geocoderResult.getGeometry().getLocation().getLng().toString());
            cities.add(city);
        }
        return cities;
    }

}
