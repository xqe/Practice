package com.example.xieqe.test001.annotation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.example.xieqe.test001.R;

/**
 * Created by xqe on 2018/7/1.
 */

@LayoutInject(R.layout.activity_test)

@InjectPage(TestActivity.class)
public class TestActivity extends Activity {

    @ViewInject(R.id.text_view)
    private TextView textView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectManager.intiInjectLayout(this);
        InjectManager.initInjectView(this);
        textView.setText("=====================");
        Log.e("======", "onCreate: " + getClass().getCanonicalName() );
    }


}
