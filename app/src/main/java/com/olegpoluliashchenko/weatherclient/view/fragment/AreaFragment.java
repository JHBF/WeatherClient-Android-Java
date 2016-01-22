package com.olegpoluliashchenko.weatherclient.view.fragment;

import com.olegpoluliashchenko.weatherclient.model.vo.ItemVO;

/**
 * Created by Oleg on 17.01.16.
 */
public class AreaFragment extends BaseListFragment {

    public static final String TAG = "areaFragment";

    @Override
    protected void request(){
        weatherModel.requestArea(getArguments().getString(ItemVO.NAME));
    }
}
