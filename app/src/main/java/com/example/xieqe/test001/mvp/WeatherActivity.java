package com.example.xieqe.test001.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import com.example.xieqe.test001.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xieqe on 2017/6/30.
 */

public class WeatherActivity extends Activity implements WeatherPresenter.IWeatherViewListener{
    @Bind(R.id.edit)
    EditText city;

    @Bind(R.id.button)
    Button getData;

    @Bind(R.id.weatherInfo)
    EditText weatherInfoEdit;

    WeatherPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        ButterKnife.bind(this);
        presenter = new WeatherPresenter(this);
    }

    @OnClick(R.id.button)
    public void getData(){
        presenter.getWeather(getCity());
    }

    @Override
    public String getCity() {
        return city.getText().toString();
    }

    @Override
    public void setWeather(WeatherInfo info) {
        weatherInfoEdit.setText(info.toString());
    }
}
