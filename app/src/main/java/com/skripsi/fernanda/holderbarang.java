package com.skripsi.fernanda;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class holderbarang extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private Activity mActivity;
    private ArrayList<Model> mContentList;

    public holderbarang(Context mContext, Activity mActivity, ArrayList<Model> mContentList) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.mContentList = mContentList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_holderbarang, parent, false);
        return new ViewHolder(view, viewType);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvnama,tvharga,tvdesc,tvimg,tvhargad;
        private ImageView iv;
        private CardView cardView;
        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cva);
            cardView.setOnClickListener(this);
            iv = (ImageView) itemView.findViewById(R.id.iv);
            tvnama = (TextView) itemView.findViewById(R.id.tvnama);
            tvharga = (TextView) itemView.findViewById(R.id.tvharga);
            tvdesc = (TextView) itemView.findViewById(R.id.tvdesc);
            tvimg = (TextView) itemView.findViewById(R.id.tvimg);
            tvhargad = (TextView) itemView.findViewById(R.id.tvhargad);
        }

        @Override
        public void onClick(View view) {
            Context ctx = ViewHolder.this.cardView.getContext();
            Intent intent = new Intent(ctx,jumlahpesanan.class);
            intent.putExtra("image", tvimg.getText());
            intent.putExtra("hargad", tvhargad.getText());
            intent.putExtra("jenis", tvnama.getText());
            intent.putExtra("harga", tvharga.getText());
            String a = tvdesc.getText().toString();
            if(a.contains("kompor")){
                intent.putExtra("tipe", "kompor");
            }
            if(a.contains("tabung")){
                intent.putExtra("tipe", "tabung");
            }
            if(a.contains("pipa")){
                intent.putExtra("tipe", "pipa");
            }

            ctx.startActivity(intent);

        }
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder mainHolder, int position) {
        ViewHolder holder = (ViewHolder) mainHolder;
        final Model model = mContentList.get(position);
        // setting data over views
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        String hargarp = kursIndonesia.format(Integer. parseInt(model.getHarga()));
        Picasso.get().load(model.getGambar()).into(holder.iv);
        holder.tvnama.setText(model.getJenis());
        holder.tvharga.setText(hargarp);
        holder.tvdesc.setText(model.getDeskripsi());
        holder.tvhargad.setText(model.getHarga());
        holder.tvimg.setText(model.getGambar());

    }
    @Override
    public int getItemCount() {
        return mContentList.size();
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

}