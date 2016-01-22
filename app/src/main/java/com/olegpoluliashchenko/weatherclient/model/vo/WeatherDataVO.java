package com.olegpoluliashchenko.weatherclient.model.vo;

/**
 * Created by Oleg on 16.01.16.
 */
public class WeatherDataVO extends ItemVO {

    public static final String NIGHT = "ночь";
    public static final String MORNING = "утро";
    public static final String DAY = "день";
    public static final String EVENING = "вечер";

    private String[] temperature;
    private String[] seasonTemperature;
    private String[] pressure;
    private String[] humidity;
    private String[] wind;

    public WeatherDataVO(String name, String link){
        super(name, link);
        temperature = new String[8];
        seasonTemperature = new String[8];
        pressure = new String[8];
        humidity = new String[8];
        wind = new String[8];
    }

    public String[] getTemperature() {
        return temperature;
    }

    public void setTemperature(String[] temperature) {
        this.temperature = temperature;
    }

    public String[] getSeasonTemperature() {
        return seasonTemperature;
    }

    public void setSeasonTemperature(String[] seasonTemperature) {
        this.seasonTemperature = seasonTemperature;
    }

    public String[] getPressure() {
        return pressure;
    }

    public void setPressure(String[] pressure) {
        this.pressure = pressure;
    }

    public String[] getHumidity() {
        return humidity;
    }

    public void setHumidity(String[] humidity) {
        this.humidity = humidity;
    }

    public String[] getWind() {
        return wind;
    }

    public void setWind(String[] wind) {
        this.wind = wind;
    }
}
