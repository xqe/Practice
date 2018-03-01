package com.example.xieqe.test001.mvp;


import android.util.Log;

/**
 * Created by xieqe on 2017/6/30.
 */

public class WeatherPresenter implements IContract.IPresenter {

    private WeatherModel weatherModel;
    private IContract.IView view;

    public WeatherPresenter(IContract.IView view){
        this.view = view;
        weatherModel = WeatherModel.getInstance();
    }

    @Override
    public void getWeather() {
        String city = view.getCityName();
        WeatherInfo info = weatherModel.getWeather(city);
        view.showWeather(info);
    }

    @Override
    public void destroy() {
        Log.i("WeatherPresenter", "destroy: ");
    }
}

