package com.olegpoluliashchenko.weatherclient.model.command;

import com.olegpoluliashchenko.weatherclient.model.vo.ItemVO;
import com.olegpoluliashchenko.weatherclient.model.vo.WeatherDataVO;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Oleg on 15.01.16.
 */
public class ParseWeatherByDateCommand extends ParseCommand {

    @Override
    public Map<String, ItemVO> execute(Document document) {
        LinkedHashMap<String, ItemVO> linkedHashMap = new LinkedHashMap<String, ItemVO>();
        WeatherDataVO itemVO = new WeatherDataVO("WeatherData", "WeatherData");

        Elements maxHeightElement = document.select("table[class=weatherDetails]");
        itemVO.setTemperature(getItems(maxHeightElement.select("tr[class=temperature]").select("td")));
        itemVO.setSeasonTemperature(getItems(maxHeightElement.select("tr[class=temperatureSens]").select("td")));
        itemVO.setPressure(getItems(maxHeightElement.select("tr[class=gray]").get(0).select("td")));
        itemVO.setHumidity(getItems(maxHeightElement.select("tr").get(6).select("td")));
        itemVO.setWind(getItems(maxHeightElement.select("tr").get(7).select("td")));

        linkedHashMap.put("WeatherData", itemVO);

        return linkedHashMap;
    }

    private String[] getItems(Elements elementsList){
        int index = 0;
        String[] elements = new String[8];
        for (Element elem : elementsList) {
            if(index > 7) break;
            elements[index] = elem.text();
            index++;
        }

        return elements;
    }
}
