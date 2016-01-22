package com.olegpoluliashchenko.weatherclient.view.fragment;

import com.olegpoluliashchenko.weatherclient.model.vo.ItemVO;

/**
 * Created by Oleg on 17.01.16.
 */
public class DayFragment extends BaseListFragment {

    public static final String TAG = "dayFragment";

    @Override
    protected void request() {
        weatherModel.requestDay(getArguments().getString(ItemVO.NAME));
    }
}
