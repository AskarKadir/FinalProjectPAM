//package com.example.awallet;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
//
//    private Dialog dialog;
//    private Context context;
//    private ArrayList<Model> model;
//
//    public Adapter(Context context, ArrayList<Model> model) {
//        this.context = context;
//        this.model = model;
//    }
//
//    public interface Dialog{
//        void onClick(int pos);
//    }
//
//    public void setDialog(Dialog dialog){
//        this.dialog = dialog;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View itemshow = LayoutInflater.from(parent.getContext()).inflate(R.layout.tampildata_layout,parent,false);
//        return new ViewHolder(itemshow);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        /*get data*/
//        holder.nominaldata.setText(model.get(position).get);
//    }
//
//    @Override
//    public int getItemCount() {
//        return model.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private TextView namadata,nominaldata;
//
//        public ViewHolder(@NonNull View itemShow){
//            super(itemShow);
//
//            namadata = itemShow.findViewById(R.id.tvNamaData);
//            nominaldata = itemShow.findViewById(R.id.tvNominalData);
//
//            itemShow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (dialog != null){
//                        dialog.onClick(getLayoutPosition());
//                    }
//                }
//            });
//
//
//
//        }
//
//    }
//}
