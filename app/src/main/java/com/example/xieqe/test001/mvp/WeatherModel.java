package com.example.xieqe.test001.mvp;

/**
 * Created by xieqe on 2017/6/30.
 */

public class WeatherModel {

    private WeatherModel() {
    }

    public static WeatherModel getInstance() {
        return Holder.INSTANCE;
    }

    public WeatherInfo getWeather(String city) {
        boolean isConnected = false;
        WeatherInfo info = new WeatherInfo();

        /*执行网络数据请求操作，获取数据,填充info
        *
        *
        *
        * */

        return info;
    }

    private static class Holder {
        private static final WeatherModel INSTANCE = new WeatherModel();
    }
}
