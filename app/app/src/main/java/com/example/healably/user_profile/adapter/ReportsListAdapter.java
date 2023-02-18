package com.example.healably.user_profile.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healably.R;
import com.example.healably.user_profile.model.UserData;

import java.util.List;

/**
 * Adaptador de dados para a funcionalidade Relatórios*/
public class ReportsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<UserData> items;
    private final OnItemClickListener itemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Object item);
    }

    class ReportsViewHolder extends RecyclerView.ViewHolder {
        TextView titulo;
        TextView data;
        TextView valor;
        ReportsViewHolder(View view) {
            super(view);
            titulo = view.findViewById(R.id.report_title);
            data = view.findViewById(R.id.report_date);
            valor = view.findViewById(R.id.report_value);
        }

        public void bind(final Object item, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public ReportsListAdapter(List<UserData> items, OnItemClickListener clickListener) {
        this.items = items;
        this.itemClickListener = clickListener;
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reports_item, parent, false);

        return new ReportsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        UserData userData = items.get(position);

        ReportsViewHolder reportsViewHolder = (ReportsViewHolder) holder;

        reportsViewHolder.titulo.setText(userData.getValueType());
        reportsViewHolder.data.setText(userData.getDate());
        reportsViewHolder.valor.setText(String.valueOf(userData.getValue()));
        reportsViewHolder.bind(items.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

