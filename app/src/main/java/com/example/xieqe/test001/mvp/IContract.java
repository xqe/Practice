package com.example.xieqe.test001.mvp;

/**
 * Created by xieqe on 2018/2/28.
 */

public interface IContract {

    interface IView {

        @CacheMethod
        String getCityName();

        void showWeather(WeatherInfo info);
    }

    interface IPresenter {
        void getWeather();

        void destroy();
    }
}
