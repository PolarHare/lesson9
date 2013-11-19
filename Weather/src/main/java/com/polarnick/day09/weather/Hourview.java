package com.polarnick.day09.weather;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.polarnick.day09.R;
import com.polarnick.day09.Utils;
import com.polarnick.day09.entities.ForecastData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Date: 19.11.13
 *
 * @author Nickolay Polyarniy aka PolarNick
 */
public class HourView extends RelativeLayout {
    private static final DateFormat HOURS_FORMATTER = new SimpleDateFormat("HH:mm", Locale.UK);

    private final Context context;
    private final ForecastData forecast;
    private final int hoursRange;

    public HourView(Context context, int hoursRange, ForecastData forecast) {
        super(context);
        this.context = context;
        this.forecast = forecast;
        this.hoursRange = hoursRange;
    }

    public void init() {
        TextView date = new TextView(context);
        date.setId(1);
        date.setText(Utils.formatHoursRange(new Date(forecast.getTime()), hoursRange, HOURS_FORMATTER));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        date.setLayoutParams(params);
        date.setGravity(Gravity.CENTER_HORIZONTAL);
        addView(date);

        ImageView mainImage = new ImageView(context);
        mainImage.setId(2);
        mainImage.setImageResource(getResources().getIdentifier(forecast.getIconType(), "drawable", context.getPackageName()));
        mainImage.setAdjustViewBounds(true);
        mainImage.setMaxWidth(Math.min(((View) this.getParent()).getWidth() / 4, Utils.BIG_WEATHER_ICONS_WIDTH));
        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, date.getId());
        mainImage.setLayoutParams(params);
        addView(mainImage, params);

        TextView temperature = new TextView(context);
        temperature.setId(3);
        temperature.setText(Utils.formatTemperature(forecast.getTemperature()));
        temperature.setTextSize(getResources().getDimension(R.dimen.todayTemperatureSize));
        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RIGHT_OF, mainImage.getId());
        params.addRule(ALIGN_TOP, mainImage.getId());
        temperature.setLayoutParams(params);
        addView(temperature, params);

        TextView summary = new TextView(context);
        summary.setId(4);
        summary.setText(forecast.getSummary());
        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RIGHT_OF, mainImage.getId());
        params.addRule(BELOW, temperature.getId());
        summary.setLayoutParams(params);
        addView(summary, params);

        if (forecast.getPrecipIntensity() > 0) {
            ImageView smallPrecipImage = new ImageView(context);
            smallPrecipImage.setId(5);
            smallPrecipImage.setImageResource(getResources().getIdentifier("small_" + forecast.getPrecipType(), "drawable", context.getPackageName()));
            smallPrecipImage.setAdjustViewBounds(true);
            params = new LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
            params.addRule(BELOW, summary.getId());
            params.addRule(RIGHT_OF, mainImage.getId());
            smallPrecipImage.setLayoutParams(params);
            addView(smallPrecipImage, params);

            TextView precipProbability = new TextView(context);
            precipProbability.setId(6);
            precipProbability.setText(" Probability " + Utils.DEFIS + " " + forecast.getPrecipProbability() + " ");
            params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RIGHT_OF, smallPrecipImage.getId());
            params.addRule(ALIGN_TOP, smallPrecipImage.getId());
            precipProbability.setLayoutParams(params);
            addView(precipProbability, params);

            ImageView probabilityImage = new ImageView(context);
            probabilityImage.setId(7);
            probabilityImage.setImageResource(R.drawable.small_percentage);
            probabilityImage.setAdjustViewBounds(true);
            params = new LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()));
            params.addRule(RIGHT_OF, precipProbability.getId());
            params.addRule(ALIGN_BOTTOM, precipProbability.getId());
            probabilityImage.setLayoutParams(params);
            addView(probabilityImage, params);

            TextView precipIntensity = new TextView(context);
            precipIntensity.setId(8);
            precipIntensity.setText(" Intencity " + Utils.DEFIS + " " + Utils.roundToSignificantFigures(forecast.getPrecipIntensity(), 2) + " ");
            params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(RIGHT_OF, smallPrecipImage.getId());
            params.addRule(BELOW, precipProbability.getId());
            precipIntensity.setLayoutParams(params);
            addView(precipIntensity, params);

            ImageView intensityImage = new ImageView(context);
            intensityImage.setId(9);
            intensityImage.setImageResource(R.drawable.small_biceps);
            intensityImage.setAdjustViewBounds(true);
            params = new LayoutParams((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()),
                    (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics()));
            params.addRule(RIGHT_OF, precipIntensity.getId());
            params.addRule(ALIGN_BOTTOM, precipIntensity.getId());
            intensityImage.setLayoutParams(params);
            addView(intensityImage, params);
        }
    }
}
