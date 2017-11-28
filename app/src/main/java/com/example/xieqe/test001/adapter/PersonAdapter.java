package com.example.xieqe.test001.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xieqe.test001.Bean.Person;
import com.example.xieqe.test001.R;

import java.util.ArrayList;

/**
 * Created by xieqe on 2017/11/27.
 */

public class PersonAdapter extends RecyclerView.Adapter<ViewHolder> {
    private ArrayList<Person> persons;

    public PersonAdapter(ArrayList<Person> persons) {
        this.persons = persons;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item,parent,false);
        return ViewHolder.createViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView.findViewById(R.id.name);
        textView.setText(persons.get(position).getName());

        ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.image);
        imageView.setBackground(persons.get(position).getPortrait());
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}
