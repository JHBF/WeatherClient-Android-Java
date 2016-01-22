package com.olegpoluliashchenko.weatherclient.model;

import android.os.AsyncTask;

import com.olegpoluliashchenko.weatherclient.model.command.ParseCityCommand;
import com.olegpoluliashchenko.weatherclient.model.command.ParseCommand;
import com.olegpoluliashchenko.weatherclient.model.command.ParseRegionCommand;
import com.olegpoluliashchenko.weatherclient.model.command.ParseWeatherByDateCommand;
import com.olegpoluliashchenko.weatherclient.model.command.ParseWeatherCommand;
import com.olegpoluliashchenko.weatherclient.model.vo.ItemVO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oleg on 10.01.16.
 */
public class WeatherModel {

    private static WeatherModel weatherModel;

    private RequestTask requestTask;
    private Map<String, ItemVO> globalHashMap;
    private Map<String, ItemVO> currentHashMap;
    private ItemVO currentRegion;
    private ItemVO currentArea;
    private ItemVO currentCity;
    private ItemVO currentDay;
    private IWeatherModelCallback callbackItem;

    private ParseCommand parseCommand;
    private ParseCommand parseRegionCommand;
    private ParseCommand parseCityCommand;
    private ParseCommand parseWeatherCommand;
    private ParseCommand parseWeatherByDateCommand;

    private WeatherModel(){
        globalHashMap = new HashMap<String, ItemVO>();

        parseRegionCommand = new ParseRegionCommand();
        parseCityCommand = new ParseCityCommand();
        parseWeatherCommand = new ParseWeatherCommand();
        parseWeatherByDateCommand = new ParseWeatherByDateCommand();
    }

    public static WeatherModel getInstance(){
        if(weatherModel == null){
            return  weatherModel = new WeatherModel();
        }
        else{
            return weatherModel;
        }
    }

    public void setCallbackItem(IWeatherModelCallback callbackItem) {
        if(this.callbackItem == null) {
            this.callbackItem = callbackItem;
        }
    }

    public void requestRegion(){
        currentHashMap = globalHashMap;
        parseCommand = parseRegionCommand;

        startTask("https://sinoptik.ua/украина");
    }

    public void requestArea(String region){
        currentRegion = globalHashMap.get(region);
        String link  = currentRegion.getLink();
        currentHashMap = currentRegion.getHashMap();
        parseCommand = new ParseRegionCommand();

        startTask("https:" + link);
    }

    public void requestCity(String area){
        currentArea = currentRegion.getHashMap().get(area);
        String link  = currentArea.getLink();
        currentHashMap = currentArea.getHashMap();
        parseCommand = parseCityCommand;

        startTask("https:" + link);
    }

    public void requestDay(String city){
        currentCity = currentArea.getHashMap().get(city);
        String link  = currentCity.getLink();
        currentHashMap = currentCity.getHashMap();
        parseCommand = parseWeatherCommand;

        startTask("https:" + link);
    }

    public void requestWeather(String day){
        currentDay = currentCity.getHashMap().get(day);
        String link  = currentDay.getLink();
        currentHashMap = currentDay.getHashMap();
        parseCommand = parseWeatherByDateCommand;

        startTask("https:" + link);
    }

    private void startTask(String link){
        if(requestTask == null && currentHashMap.size() == 0) {
            requestTask = new RequestTask();
            requestTask.execute(link);
        }
        else{
            callbackItem.weatherModelCallback(currentHashMap);
            currentHashMap = null;
            callbackItem = null;
            parseCommand = null;
            requestTask = null;
        }
    }

    private void callback(){
        callbackItem.weatherModelCallback(currentHashMap);
        callbackItem = null;
    }

    private class RequestTask extends AsyncTask<String, Void, Map<String, ItemVO>> {

        @Override
        protected Map<String, ItemVO> doInBackground(String... params) {

            Document doc = null;
            try {
                doc = Jsoup.connect(params[0]).userAgent("Mozilla/5.0").get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (doc != null && parseCommand != null) {
                return parseCommand.execute(doc);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Map<String, ItemVO> result) {
            super.onPostExecute(result);
            currentHashMap.putAll(result);
            callback();
            currentHashMap = null;
            requestTask = null;
        }
    }
}
