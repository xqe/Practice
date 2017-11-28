package com.example.xieqe.test001;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.xieqe.test001.Bean.Person;
import com.example.xieqe.test001.adapter.HeaderFooterDecorator;
import com.example.xieqe.test001.adapter.PersonAdapter;
import com.example.xieqe.test001.view.FooterView;
import com.example.xieqe.test001.view.HeaderView;
import com.example.xieqe.test001.view.LetterView_wx;
import com.example.xieqe.test001.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xieqe on 2017/8/31.
 */

public class SchemeActivity extends Activity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.letterView)
    LetterView_wx letterView;
    private ArrayList<Person> persons;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme);
        ButterKnife.bind(this);
        Log.i("SchemeActivity", "onCreate: " + getIntent().getData().getQueryParameter("goodId"));
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("com.xqe.testBroadcast"));


        initData();
        initRecyclerView();
    }

    private void initData(){
        persons = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Person person = new Person(String.valueOf(i),getResources().getDrawable(R.mipmap.ic_launcher),"1232232");
            persons.add(person);
        }
    }

    private void initRecyclerView(){

        HeaderFooterDecorator adapter = new HeaderFooterDecorator(new PersonAdapter(persons));
        adapter.addHeaderView(new HeaderView(this));
        adapter.addFooterView(new FooterView(this,persons.size()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL));
        recyclerView.setAdapter(adapter);
    }


}
