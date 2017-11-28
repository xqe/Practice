package com.example.xieqe.test001.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xieqe on 2017/11/27.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    private View convertView;

    public ViewHolder(View itemView) {
        super(itemView);
        this.convertView = itemView;
    }

    public static ViewHolder createViewHolder(final View itemView){

        return new ViewHolder(itemView);
    }


}
