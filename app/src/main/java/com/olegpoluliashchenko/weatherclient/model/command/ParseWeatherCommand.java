package com.olegpoluliashchenko.weatherclient.model.command;

import com.olegpoluliashchenko.weatherclient.model.vo.ItemVO;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

/**
 * Created by Oleg on 15.01.16.
 */
public class ParseWeatherCommand extends ParseCommand {
    @Override
    public HashMap<String, ItemVO> execute(Document document) {

        Elements maxHeightElement = document.select("a[class=day-link]");

        HashMap<String, ItemVO> hashMap = new HashMap<String, ItemVO>();
        ItemVO itemVO;

        for (Element elem : maxHeightElement) {
            itemVO = new ItemVO(elem.text(), elem.attr("data-link"));
            hashMap.put(elem.text(), itemVO);
        }

        return hashMap;
    }
}
