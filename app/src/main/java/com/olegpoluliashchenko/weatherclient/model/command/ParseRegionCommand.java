package com.olegpoluliashchenko.weatherclient.model.command;

import com.olegpoluliashchenko.weatherclient.model.vo.ItemVO;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

/**
 * Created by Oleg on 15.01.16.
 */
public class ParseRegionCommand extends ParseCommand {

    @Override
    public HashMap<String, ItemVO> execute(Document document) {

        Elements maxHeightElement = document.select("div[class=maxHeight]");
        Elements linksElements = maxHeightElement.select("a[href]");

        HashMap<String, ItemVO> hashMap = new HashMap<String, ItemVO>();
        ItemVO itemVO;

        for (Element elem : linksElements) {
            itemVO = new ItemVO(elem.text(), elem.attr("href"));
            hashMap.put(elem.text(), itemVO);
        }

        return hashMap;
    }
}
