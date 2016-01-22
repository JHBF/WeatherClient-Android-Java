package com.olegpoluliashchenko.weatherclient.view.fragment;

/**
 * Created by Oleg on 16.01.16.
 */
public class RegionFragment extends BaseListFragment {

    public static final String TAG = "regionFragment";

    @Override
    protected void request(){
        weatherModel.requestRegion();
    }
}
