package com.example.xieqe.test001.adapter;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by xieqe on 2017/11/27.
 */

public class HeaderFooterDecorator extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private SparseArrayCompat<View> headerViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> footerViews = new SparseArrayCompat<>();
    private RecyclerView.Adapter adapter;
    private ItemClickListener listener;


    public HeaderFooterDecorator(RecyclerView.Adapter adapter){
        this.adapter = adapter;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPos(position))
        {
            return headerViews.keyAt(position);

        }else if (isFooterPos(position))
        {
            return footerViews.keyAt(position - getHeaderCount() - getContentItemCount());
        }
        return adapter.getItemViewType(position - getHeaderCount());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (headerViews.get(viewType) != null)
        {
            return ViewHolder.createViewHolder(headerViews.get(viewType));

        } else if (footerViews.get(viewType) != null)
        {
            return ViewHolder.createViewHolder(footerViews.get(viewType));
        }

        return adapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (isHeaderPos(position)){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onHeaderClick(position);
                }
            });
            return;
        }
        if (isFooterPos(position)){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onFooterClick(position - getHeaderCount() - getContentItemCount());
                }
            });
            return;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position - getHeaderCount());
            }
        });
        adapter.onBindViewHolder(holder,position - getHeaderCount());
    }

    @Override
    public int getItemCount() {
        return getContentItemCount() + getHeaderCount() + getFooterCount();
    }

    public void addHeaderView(View view){
        headerViews.put(1000 + headerViews.size(),view);
    }

    public void addFooterView(View view){
        footerViews.put(2000 + footerViews.size(),view);
    }

    public boolean isHeaderPos(int position){

        return position < getHeaderCount();
    }

    public boolean isFooterPos(int position){

        return position >= getHeaderCount() + getContentItemCount();
    }

    public int getHeaderCount(){
        return headerViews.size();
    }

    public int getFooterCount(){
        return footerViews.size();
    }

    public int getContentItemCount(){
        return adapter.getItemCount();
    }

    public void setOnItemClickListener(ItemClickListener listener){
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
        void onHeaderClick(int headerIndex);
        void onFooterClick(int footerIndex);
    }
}
