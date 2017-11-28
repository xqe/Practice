package com.example.xieqe.test001.mvp;


/**
 * Created by xieqe on 2017/6/30.
 */

public class WeatherPresenter {

    WeatherModel weatherModel;
    IWeatherViewListener viewListener;

    public WeatherPresenter(IWeatherViewListener viewListener){
        this.viewListener = viewListener;
    }

    public void getWeather(String city){
        WeatherInfo info = weatherModel.getWeather(city);
        viewListener.setWeather(info);
    }


    /**在View中实现*/
    public interface IWeatherViewListener{
        String getCity();
        void setWeather(WeatherInfo info);
    }

}

