package com.olegpoluliashchenko.weatherclient.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.olegpoluliashchenko.weatherclient.R;
import com.olegpoluliashchenko.weatherclient.model.vo.ItemVO;

import java.util.List;

/**
 * Created by Oleg on 16.01.16.
 */
public class RegionAdapter extends BaseAdapter {

    private List<ItemVO> list;
    private LayoutInflater layoutInflater;

    public RegionAdapter(Context context, List<ItemVO> list){
        this.list = list;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(view == null){
            view = layoutInflater.inflate(R.layout.region_item, parent, false);
        }

        ItemVO itemVO = (ItemVO)getItem(position);

        TextView regionName = (TextView)view.findViewById(R.id.regionName);
        regionName.setText(itemVO.getName());

        return view;
    }
}
