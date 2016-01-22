package com.olegpoluliashchenko.weatherclient.model.command;

import com.olegpoluliashchenko.weatherclient.model.vo.ItemVO;

import org.jsoup.nodes.Document;

import java.util.Map;

/**
 * Created by Oleg on 15.01.16.
 */
public abstract class ParseCommand {

    public abstract Map<String, ItemVO> execute(Document document);
}
