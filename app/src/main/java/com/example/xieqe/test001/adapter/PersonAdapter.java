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
    private ArrayList<String> tags = new ArrayList<>();

    public PersonAdapter(ArrayList<Person> persons) {
        this.persons = persons;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (!tags.contains(persons.get(viewType).getFirstLetter())){
            tags.add(persons.get(viewType).getFirstLetter());
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item_with_letter,parent,false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item,parent,false);
        }

        view.setTag(persons.get(viewType).getFirstLetter());
        return ViewHolder.createViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView firstLetter = (TextView) holder.itemView.findViewById(R.id.first_letter);
        if (firstLetter != null) {
            firstLetter.setText((String) holder.itemView.getTag());
        }
        TextView textView = (TextView) holder.itemView.findViewById(R.id.name);
        textView.setText(persons.get(position).getName() + "---" + (position + 1));
        ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.image);
        imageView.setBackground(persons.get(position).getPortrait());
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }

    public int getLetterPosition(String letter){
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i).getFirstLetter().equals(letter)){
                return i;
            }
        }
        return -1;
    }


}
