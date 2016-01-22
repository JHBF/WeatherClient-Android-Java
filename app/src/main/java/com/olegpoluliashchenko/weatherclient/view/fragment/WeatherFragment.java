package com.olegpoluliashchenko.weatherclient.view.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.olegpoluliashchenko.weatherclient.R;
import com.olegpoluliashchenko.weatherclient.model.IWeatherModelCallback;
import com.olegpoluliashchenko.weatherclient.model.WeatherModel;
import com.olegpoluliashchenko.weatherclient.model.vo.ItemVO;
import com.olegpoluliashchenko.weatherclient.model.vo.WeatherDataVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Oleg on 18.01.16.
 */
public class WeatherFragment extends Fragment  implements IWeatherModelCallback {

    public static final String TAG = "weatherFragment";

    protected WeatherModel weatherModel;
    protected List<ItemVO> list;

    private View temperatureView;
    private View temperatureSeasonView;
    private View pressureView;
    private View humidityView;
    private View windView;

    private LinearLayout linearContainer;
    private String divider;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_fragment, container, false);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            divider = " ";
        }
        else{
            divider = "\n";
        }

        linearContainer = (LinearLayout)view.findViewById(R.id.linearContainer);
        LayoutInflater layoutInflater = getLayoutInflater(savedInstanceState);

        temperatureView = layoutInflater.inflate(R.layout.data_item, null, false);
        linearContainer.addView(temperatureView);

        temperatureSeasonView = layoutInflater.inflate(R.layout.data_item, null, false);
        linearContainer.addView(temperatureSeasonView);

        pressureView = layoutInflater.inflate(R.layout.data_item, null, false);
        linearContainer.addView(pressureView);

        humidityView = layoutInflater.inflate(R.layout.data_item, null, false);
        linearContainer.addView(humidityView);

        windView = layoutInflater.inflate(R.layout.data_item, null, false);
        linearContainer.addView(windView);

        list = new ArrayList<>();

        weatherModel = WeatherModel.getInstance();
        weatherModel.setCallbackItem(this);
        weatherModel.requestWeather(getArguments().getString(ItemVO.NAME));

        return view;
    }

    @Override
    public void weatherModelCallback(Map<String, ItemVO> map) {
        list.addAll(map.values());
        addData();
    }

    private void addData() {
        WeatherDataVO weatherDataVO = (WeatherDataVO)list.get(0);

        setData(temperatureView, weatherDataVO.getTemperature(), getResources().getString(R.string.temperature));
        setData(temperatureSeasonView, weatherDataVO.getSeasonTemperature(), getResources().getString(R.string.seasonTemperature));
        setData(pressureView, weatherDataVO.getPressure(), getResources().getString(R.string.pressure));
        setData(humidityView, weatherDataVO.getHumidity(), getResources().getString(R.string.humidity));
        setData(windView, weatherDataVO.getWind(), getResources().getString(R.string.wind));
    }

    private void setData(View view, String[] data, String titleText){
        textView = (TextView)view.findViewById(R.id.titleText);
        textView.setText(titleText);
        if(data[4] != null){
            textView = (TextView)view.findViewById(R.id.nightText);
            textView.setText(data[0] + divider + data[1]);
            textView = (TextView)view.findViewById(R.id.morningText);
            textView.setText(data[2] + divider + data[3]);
            textView = (TextView)view.findViewById(R.id.dayText);
            textView.setText(data[4] + divider + data[5]);
            textView = (TextView)view.findViewById(R.id.eveningText);
            textView.setText(data[6] + divider + data[7]);
        }
        else{
            textView = (TextView)view.findViewById(R.id.nightText);
            textView.setText(data[0]);
            textView = (TextView)view.findViewById(R.id.morningText);
            textView.setText(data[1]);
            textView = (TextView)view.findViewById(R.id.dayText);
            textView.setText(data[2]);
            textView = (TextView)view.findViewById(R.id.eveningText);
            textView.setText(data[3]);
        }
    }
}
