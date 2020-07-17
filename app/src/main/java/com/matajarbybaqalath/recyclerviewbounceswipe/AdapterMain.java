package com.matajarbybaqalath.recyclerviewbounceswipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterMain extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int HEADER_ITEM = 123;
    private int DATA_ITEM = 321;

    private ArrayList<String> data;

    public AdapterMain(ArrayList<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEADER_ITEM) {
            return new HeaderVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_header_item, parent, false));
        } else {
            return new DataItemVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_data_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == DATA_ITEM) {
            DataItemVH dataItemVH = (DataItemVH) holder;
            dataItemVH.txtItem.setText("Item " + position);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_ITEM;
        } else {
            return DATA_ITEM;
        }
    }

    static class HeaderVH extends RecyclerView.ViewHolder {
        private TextView txtTitle;

        public HeaderVH(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);

        }
    }

    static class DataItemVH extends RecyclerView.ViewHolder {
        private TextView txtItem;

        public DataItemVH(@NonNull View itemView) {
            super(itemView);
            txtItem = itemView.findViewById(R.id.txtItem);
        }
    }

    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }
}
