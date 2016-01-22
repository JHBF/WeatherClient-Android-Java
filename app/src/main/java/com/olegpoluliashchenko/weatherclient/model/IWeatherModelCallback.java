package com.olegpoluliashchenko.weatherclient.model;

import com.olegpoluliashchenko.weatherclient.model.vo.ItemVO;

import java.util.Map;

/**
 * Created by Oleg on 16.01.16.
 */
public interface IWeatherModelCallback {

    public void weatherModelCallback(Map<String, ItemVO> map);
}
