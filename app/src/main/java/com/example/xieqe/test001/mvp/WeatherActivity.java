package com.example.xieqe.test001.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;

import com.example.xieqe.test001.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by xieqe on 2017/6/30.
 */

public class WeatherActivity extends Activity implements IContract.IView{
    @BindView(R.id.edit)
    EditText city;
    @BindView(R.id.button)
    Button getData;
    @BindView(R.id.weatherInfo)
    EditText weatherInfoEdit;

    IContract.IPresenter presenter;
    ViewProxy viewProxy;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        ButterKnife.bind(this);

        viewProxy = new ViewProxy();
        viewProxy.bind(this);
        //传入viewProxy.proxy生成的代理对象
        presenter = new WeatherPresenter(viewProxy.proxy(IContract.IView.class));
    }

    @OnClick(R.id.button)
    public void onClick(){
        presenter.getWeather();
    }

    @Override
    public String getCityName() {
        return city.getText().toString();
    }

    @Override
    public void showWeather(WeatherInfo info) {
        weatherInfoEdit.setText(info.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroy();
        viewProxy.destroy();
    }
}
