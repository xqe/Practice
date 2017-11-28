package com.example.xieqe.test001.mvc;

import com.example.xieqe.test001.mvp.WeatherInfo;

/**
 * Created by xieqe on 2017/6/30.
 */

public class WeatherModel {

    private IWeatherDataListener listener;

    public WeatherModel(IWeatherDataListener listener){
        this.listener = listener;
    }

    public void getWeather(String city){
        boolean isConnected = false;
        WeatherInfo info = new WeatherInfo();
        String reason = "";

        /*执行网络操作，获取数据,填充info
        *
        *
        *
        * */
        if (isConnected){
            listener.onSuccess(info);
        }else{
            listener.onError(reason);
        }
    }

    public interface IWeatherDataListener{
        void onSuccess(WeatherInfo info);
        void onError(String reason);
    }
}
