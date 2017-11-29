package com.example.xieqe.test001;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.xieqe.test001.Bean.Person;
import com.example.xieqe.test001.Util.LetterComparator;
import com.example.xieqe.test001.Util.PinyinUtil;
import com.example.xieqe.test001.adapter.HeaderFooterDecorator;
import com.example.xieqe.test001.adapter.PersonAdapter;
import com.example.xieqe.test001.view.FooterView;
import com.example.xieqe.test001.view.HeaderView;
import com.example.xieqe.test001.view.LetterDecoration;
import com.example.xieqe.test001.view.LetterView_wx;
import com.example.xieqe.test001.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xieqe on 2017/6/14.
 * 委托
 */

public class TestFragment extends Fragment implements HeaderFooterDecorator.ItemClickListener{

    private final static String TAG = "TestFragment";

    private ParentListener parentListener;
    View view;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.letterView)
    LetterView_wx letterView;
    private ArrayList<Person> persons;

    public interface ParentListener{
        void clickPosition(int position);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parentListener = (ParentListener)getActivity();
    }

    @Override
    public void onItemClick(int position) {
        Log.e(TAG, "onItemClick: " + position);
    }

    @Override
    public void onHeaderClick(int headerIndex) {
        Log.e(TAG, "onHeaderClick: " + headerIndex );
    }

    @Override
    public void onFooterClick(int footerIndex) {
        Log.e(TAG, "onFooterClick: " + footerIndex );
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment,container,false);
        ButterKnife.bind(this,view);
        initData();
        initRecyclerView();
        return view;
    }

    private void initData(){
        persons = new ArrayList<>();
        for (int i = 0; i < Person.DATA.length; i++) {
            Person person = new Person(Person.DATA[i],getResources().getDrawable(R.mipmap.ic_launcher),"1232232");
            String pinyin = PinyinUtil.parseToPinyin(person.getName());
            person.setPinyin(pinyin);
            persons.add(person);
        }
        Collections.sort(persons,new LetterComparator());
    }

    private void initRecyclerView(){

        HeaderFooterDecorator adapter = new HeaderFooterDecorator(new PersonAdapter(persons));
        adapter.addHeaderView(new HeaderView(getActivity()));
        adapter.addFooterView(new FooterView(getActivity(),persons.size()));
        adapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new LetterDecoration(getActivity()));
        recyclerView.setAdapter(adapter);
    }

}
