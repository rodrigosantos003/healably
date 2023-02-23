package com.example.healably.user_profile.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healably.R;
import com.example.healably.user_profile.controller.UserDataController;
import com.example.healably.user_profile.model.UserData;

import java.util.List;

/**
 * Adaptador de dados para a funcionalidade Relatórios*/
public class ReportsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
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

    public ReportsListAdapter(Context context, List<UserData> items, OnItemClickListener clickListener) {
        this.context = context;
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

        String valueType = userData.getValueType();
        String titulo;
        String valor = String.valueOf(userData.getValue());

        switch (valueType){
            case UserDataController.WEIGHT:
                titulo = context.getString(R.string.weight);
                valor += " " + context.getString(R.string.kg);
                break;
            case UserDataController.HEIGHT:
                titulo = context.getString(R.string.height);
                valor += " " + context.getString(R.string.m);
                break;
            case UserDataController.ABDOMINAL_PERIMETER:
                titulo = context.getString(R.string.abdominal_perimeter);
                valor += " " + context.getString(R.string.cm);
                break;
            case UserDataController.BLOOD_SUGAR:
                titulo = context.getString(R.string.blood_sugar);
                valor += " " + context.getString(R.string.mg_dl);
                break;
            case UserDataController.SYS_BLOOD_PRESSURE:
                titulo = context.getString(R.string.sys_bp);
                valor += " " + context.getString(R.string.mm_hg);
                break;
            case UserDataController.DIA_BLOOD_PRESSURE:
                titulo = context.getString(R.string.dia_bp);
                valor += " " + context.getString(R.string.mm_hg);
                break;
            case UserDataController.HEART_RATE:
                titulo = context.getString(R.string.heart_rate);
                valor += " " + context.getString(R.string.bpm);
                break;
            default:
                titulo = "";
                break;
        }

        reportsViewHolder.titulo.setText(titulo);
        reportsViewHolder.data.setText(userData.getDate());
        reportsViewHolder.valor.setText(valor);
        reportsViewHolder.bind(items.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

