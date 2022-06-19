package com.example.awallet;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    ArrayList<Model> modelArrayList;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int pos);
    }

    public void setDialog(Dialog dialog){
        this.dialog = dialog;
    }

    public Adapter(Context context, ArrayList<Model> modelArrayList) {
        this.context = context;
        this.modelArrayList = modelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemshow = LayoutInflater.from(parent.getContext()).inflate(R.layout.tampildata_layout,parent,false);
        return new ViewHolder(itemshow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /*get data*/
        Model mdl = modelArrayList.get(position);
        holder.nominaldata.setText(mdl.getNominalPemasukan());
        holder.namadata.setText(mdl.getNamaPemasukan());
    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView namadata,nominaldata;

        public ViewHolder(@NonNull View itemShow){
            super(itemShow);

            namadata = itemShow.findViewById(R.id.tvNamaData);
            nominaldata = itemShow.findViewById(R.id.tvNominalData);

            itemShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog != null) {
                        dialog.onClick(getLayoutPosition());
                    }
                }
                });
            };
    }
}
