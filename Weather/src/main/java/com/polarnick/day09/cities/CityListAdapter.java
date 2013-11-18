package com.polarnick.day09.cities;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.polarnick.day09.R;
import com.polarnick.day09.dao.DatabaseHelperFactory;
import com.polarnick.day09.entities.City;

import java.sql.SQLException;
import java.util.List;

/**
 * Date: 18.11.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
public class CityListAdapter extends ArrayAdapter<City> {
    private final Context context;
    private final List<City> cities;
    private final View.OnClickListener deleteCityListener;

    public CityListAdapter(final Context context, final List<City> cities) {
        super(context, R.layout.city_view, cities);
        this.context = context;
        this.cities = cities;
        this.deleteCityListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                City city = (City) v.getTag();
                try {
                    DatabaseHelperFactory.getHelper().getCityDAO().deleteById(city.getId());
                } catch (SQLException e) {
                    Log.e(CityListAdapter.class.getName(), "Deleting city with name=" + city.getName() + " and id=" + city.getId() + " failed!");
                    throw new RuntimeException(e);
                }
                cities.remove(city);
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.city_view, null);
        }
        final TextView cityNameTextView = (TextView) convertView.findViewById(R.id.cityName);
        cityNameTextView.setText(cities.get(position).getName());

        final ImageButton deleteButton = (ImageButton) convertView.findViewById(R.id.deleteCityButton);
        deleteButton.setOnClickListener(deleteCityListener);
        deleteButton.setTag(cities.get(position));
        return convertView;
    }
}
