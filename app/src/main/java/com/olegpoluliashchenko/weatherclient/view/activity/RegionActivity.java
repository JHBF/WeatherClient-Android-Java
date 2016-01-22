package com.olegpoluliashchenko.weatherclient.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.olegpoluliashchenko.weatherclient.R;
import com.olegpoluliashchenko.weatherclient.model.vo.ItemVO;
import com.olegpoluliashchenko.weatherclient.view.fragment.AreaFragment;
import com.olegpoluliashchenko.weatherclient.view.fragment.BaseListFragment;
import com.olegpoluliashchenko.weatherclient.view.fragment.CityFragment;
import com.olegpoluliashchenko.weatherclient.view.fragment.IFragmentCallback;
import com.olegpoluliashchenko.weatherclient.view.fragment.RegionFragment;

/**
 * Created by Oleg on 16.01.16.
 */
public class RegionActivity extends AppCompatActivity implements IFragmentCallback {

    public static final String BUNDLE_KEY = "bundleKey";

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private String lastFragment;
    private RegionFragment regionFragment;
    private AreaFragment areaFragment;
    private CityFragment cityFragment;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.region_activity);

        fragmentManager = getSupportFragmentManager();
        createFragments();

        lastFragment = savedInstanceState != null ? savedInstanceState.getString(BaseListFragment.LAST_FRAGMENT_KEY) : "";
        bundle = savedInstanceState != null ? savedInstanceState.getBundle(BUNDLE_KEY) : null;
        addFragment(lastFragment);
    }

    @Override
    public void onBackPressed() {
        switch(lastFragment){
            case RegionFragment.TAG:
                lastFragment = "";
                break;
            case AreaFragment.TAG:
                lastFragment = RegionFragment.TAG;
                break;
            case CityFragment.TAG:
                lastFragment = AreaFragment.TAG;
                bundle = fragmentManager.findFragmentByTag(AreaFragment.TAG).getArguments();
                break;
        }
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(BaseListFragment.LAST_FRAGMENT_KEY, lastFragment);
        outState.putBundle(BUNDLE_KEY, bundle);
    }

    @Override
    public void fragmentCallback(ItemVO itemVO) {
        bundle = new Bundle();
        bundle.putString(ItemVO.NAME, itemVO.getName());
        addFragment("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        fragmentManager = null;
        fragmentTransaction = null;
        lastFragment = null;
        regionFragment = null;
        areaFragment = null;
        cityFragment = null;
        bundle = null;
    }

    private void createFragments() {
        regionFragment = new RegionFragment();
        areaFragment = new AreaFragment();
        cityFragment = new CityFragment();
    }

    private void addFragment(String last) {
        fragmentTransaction = fragmentManager.beginTransaction();

        if(fragmentManager.findFragmentByTag(RegionFragment.TAG) != null &&
                (fragmentManager.findFragmentByTag(RegionFragment.TAG).isResumed() == true ||
                        last == AreaFragment.TAG)){
            areaFragment.setArguments(bundle);
            lastFragment = AreaFragment.TAG;
            fragmentTransaction.replace(R.id.mainContainer, areaFragment, AreaFragment.TAG);
            fragmentTransaction.addToBackStack("");
            if(last == AreaFragment.TAG) {
                fragmentManager.popBackStack();
            }
        }
        else if(fragmentManager.findFragmentByTag(AreaFragment.TAG) != null &&
                (fragmentManager.findFragmentByTag(AreaFragment.TAG).isResumed() == true ||
                        last == CityFragment.TAG)){
            cityFragment.setArguments(bundle);
            lastFragment = CityFragment.TAG;
            fragmentTransaction.replace(R.id.mainContainer, cityFragment, CityFragment.TAG);
            fragmentTransaction.addToBackStack("");
            if(last == CityFragment.TAG) {
                fragmentManager.popBackStack();
            }
        }
        else if(fragmentManager.findFragmentByTag(CityFragment.TAG) != null){
            Intent intent = new Intent(this, WeatherActivity.class);
            intent.putExtra(ItemVO.NAME, bundle);
            bundle = fragmentManager.findFragmentByTag(CityFragment.TAG).getArguments();
            startActivity(intent);
        }
        else {
            lastFragment = RegionFragment.TAG;
            fragmentTransaction.replace(R.id.mainContainer, regionFragment, RegionFragment.TAG);
        }

        fragmentTransaction.commit();
    }

}
