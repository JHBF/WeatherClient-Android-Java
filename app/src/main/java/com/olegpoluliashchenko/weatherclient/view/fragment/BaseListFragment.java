package com.olegpoluliashchenko.weatherclient.view.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.olegpoluliashchenko.weatherclient.model.IWeatherModelCallback;
import com.olegpoluliashchenko.weatherclient.model.WeatherModel;
import com.olegpoluliashchenko.weatherclient.model.vo.ItemVO;
import com.olegpoluliashchenko.weatherclient.view.adapter.RegionAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Oleg on 17.01.16.
 */
public abstract class BaseListFragment extends ListFragment implements IWeatherModelCallback {

    public static final String LAST_FRAGMENT_KEY = "lastFragmentKey";

    protected WeatherModel weatherModel;
    protected List<ItemVO> list;
    protected RegionAdapter regionAdapter;
    protected IFragmentCallback fragmentCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        list = new ArrayList<>();

        weatherModel = WeatherModel.getInstance();
        weatherModel.setCallbackItem(this);
        regionAdapter = new RegionAdapter(getContext(), list);
        request();

        setListAdapter(regionAdapter);

        fragmentCallback = (IFragmentCallback)getActivity();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract void request();

    @Override
    public void weatherModelCallback(Map<String, ItemVO> map) {
        list.addAll(map.values());
        regionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        fragmentCallback.fragmentCallback(list.get(position));
    }
}
