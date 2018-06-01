package com.lithiumsheep.stratechery;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter {

    private List<T> list;

    protected BaseRecyclerAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public T getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    public void add(T item) {
        list.add(item);
        notifyItemInserted(list.size() - 1);
    }

    public void addAll(List<T> items) {
        for (T item : items) {
            add(item);
        }
    }

    public void remove(T item) {
        int position = list.indexOf(item);
        if (position > -1) {
            list.remove(item);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
}
