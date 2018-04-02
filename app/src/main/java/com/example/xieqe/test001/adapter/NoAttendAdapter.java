package com.example.xieqe.test001.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xieqe.test001.Bean.Person;
import com.example.xieqe.test001.R;

import java.util.ArrayList;

/**
 * Created by xieqe on 2018/3/20.
 */

public class NoAttendAdapter extends RecyclerView.Adapter<NoAttendAdapter.ViewHolder> {
    private ArrayList<Person> persons;

    public NoAttendAdapter(ArrayList<Person> persons) {
        this.persons = persons;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_no_attender,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(persons.get(position).getName());
    }


    @Override
    public int getItemCount() {
        return persons.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
