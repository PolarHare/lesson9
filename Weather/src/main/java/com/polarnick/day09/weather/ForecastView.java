package com.polarnick.day09.weather;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.polarnick.day09.entities.ForecastData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date: 19.11.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
public class ForecastView extends LinearLayout {

    private final Context context;
    private final DateFormat oldDateFormatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");

    public ForecastView(Context context, ForecastData forecastData) {
        super(context);
        this.context = context;
        addText(oldDateFormatter.format(new Date(forecastData.getTime())) + ": " + forecastData.getTemperature());
    }

    private void addText(String text) {
        TextView view = new TextView(context);
        view.setText(text);
        addView(view);
    }

}
