package com.example.xieqe.test001.mvc;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import com.example.xieqe.test001.R;
import com.example.xieqe.test001.mvp.WeatherInfo;
import com.example.xieqe.test001.mvp.WeatherPresenter;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xieqe on 2017/6/30.
 */

public class WeatherActivity extends Activity implements WeatherModel.IWeatherDataListener{
    @Bind(R.id.edit)
    EditText city;

    @Bind(R.id.button)
    Button getData;

    @Bind(R.id.weatherInfo)
    EditText weatherInfoEdit;

    WeatherModel weatherModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        ButterKnife.bind(this);
        weatherModel = new WeatherModel(this);
    }

    @OnClick(R.id.button)
    public void getData(){
        weatherModel.getWeather(city.getText().toString());
    }

    @Override
    public void onSuccess(WeatherInfo info) {
        weatherInfoEdit.setText(info.toString());
    }

    @Override
    public void onError(String reason) {
        weatherInfoEdit.setText(reason);
    }
}
