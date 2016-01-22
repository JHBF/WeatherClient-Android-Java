package com.olegpoluliashchenko.weatherclient.view.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.olegpoluliashchenko.weatherclient.R;
import com.olegpoluliashchenko.weatherclient.model.vo.ItemVO;
import com.olegpoluliashchenko.weatherclient.view.fragment.BaseListFragment;
import com.olegpoluliashchenko.weatherclient.view.fragment.DayFragment;
import com.olegpoluliashchenko.weatherclient.view.fragment.IFragmentCallback;
import com.olegpoluliashchenko.weatherclient.view.fragment.WeatherFragment;

/**
 * Created by Oleg on 16.01.16.
 */
public class WeatherActivity extends AppCompatActivity implements IFragmentCallback {

    public static final String BUNDLE_KEY = "bundleKey";

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private DayFragment dayFragment;
    private WeatherFragment weatherFragment;
    private String lastFragment;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);

        lastFragment = savedInstanceState != null ? savedInstanceState.getString(BaseListFragment.LAST_FRAGMENT_KEY) : "";
        bundle = savedInstanceState != null ? savedInstanceState.getBundle(BUNDLE_KEY) : getIntent().getBundleExtra(ItemVO.NAME);

        fragmentManager = getSupportFragmentManager();
        createFragments();
        addedFragment(lastFragment);
    }

    @Override
    public void onBackPressed() {
        switch(lastFragment){
            case DayFragment.TAG:
                lastFragment = "";
                break;
            case WeatherFragment.TAG:
                lastFragment = DayFragment.TAG;
                bundle = fragmentManager.findFragmentByTag(DayFragment.TAG).getArguments();
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
        addedFragment("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        fragmentManager = null;
        fragmentTransaction = null;
        dayFragment = null;
        weatherFragment = null;
        lastFragment = null;
        bundle = null;
    }

    private void createFragments() {
        dayFragment = new DayFragment();
        weatherFragment = new WeatherFragment();
    }

    private void addedFragment(String last) {
        fragmentTransaction = fragmentManager.beginTransaction();

        if(fragmentManager.findFragmentByTag(DayFragment.TAG) != null &&
                (fragmentManager.findFragmentByTag(DayFragment.TAG).isResumed() == true ||
                        last == WeatherFragment.TAG)){
            weatherFragment.setArguments(bundle);
            lastFragment = WeatherFragment.TAG;
            fragmentTransaction.replace(R.id.mainContainer, weatherFragment, WeatherFragment.TAG);
            fragmentTransaction.addToBackStack("");
            if(last == WeatherFragment.TAG) {
                fragmentManager.popBackStack();
            }
        }
        else {
            lastFragment = DayFragment.TAG;
            dayFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.mainContainer, dayFragment, DayFragment.TAG);
        }

        fragmentTransaction.commit();
    }

}
