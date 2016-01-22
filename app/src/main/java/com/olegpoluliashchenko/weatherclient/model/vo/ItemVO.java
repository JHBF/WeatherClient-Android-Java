package com.olegpoluliashchenko.weatherclient.model.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oleg on 15.01.16.
 */
public class ItemVO {

    public static final String LINK = "link";
    public static final String NAME = "name";

    private String name;
    private String link;
    private Map<String, ItemVO> hashMap;

    public ItemVO(String name, String link){
        this.name = name;
        this.link = link;
        hashMap = new HashMap<String, ItemVO>();
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public Map<String, ItemVO> getHashMap() {
        return hashMap;
    }

}
