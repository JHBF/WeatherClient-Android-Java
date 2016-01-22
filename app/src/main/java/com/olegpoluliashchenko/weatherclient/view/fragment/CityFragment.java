package com.olegpoluliashchenko.weatherclient.view.fragment;

import com.olegpoluliashchenko.weatherclient.model.vo.ItemVO;

/**
 * Created by Oleg on 17.01.16.
 */
public class CityFragment extends BaseListFragment {

    public static final String TAG = "cityFragment";

    @Override
    protected void request() {
        weatherModel.requestCity(getArguments().getString(ItemVO.NAME));
    }
}
