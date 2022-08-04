package com.skripsi.fernanda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class jumlahpesanan extends AppCompatActivity {
    TextView tvjenisp, tvjumlahp,tvtotalp,tvharga,tva;
    ImageView ivp;
    Integer jpesan, hargatotal,total,b;
    String hargad,tipe;
    private DatabaseReference mdatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jumlahpesanan);
        String image = getIntent().getStringExtra("image");
        hargad = getIntent().getStringExtra("hargad");
        String jenis = getIntent().getStringExtra("jenis");
        String harga = getIntent().getStringExtra("harga");
        tipe = getIntent().getStringExtra("tipe");
        ivp = findViewById(R.id.imageView8);
        tvjenisp = findViewById(R.id.textView14);
        tvjenisp.setText(jenis);
        tvharga = findViewById(R.id.textView15);
        tvharga.setText(harga);
        tvjumlahp = findViewById(R.id.textView20);
        tvtotalp = findViewById(R.id.textView16);
        Picasso.get().load(image).into(ivp);
        tvtotalp.setText(harga);
        tva = findViewById(R.id.textView24);
        tva.setText(tipe);
        updateharga(Integer.parseInt(tvjumlahp.getText().toString()));


    }
    public void tambah(View v){
        if(tva.getText().equals("tabung")){

        }
        jpesan = Integer.parseInt(tvjumlahp.getText().toString());
        String b = String.valueOf(jpesan+1);
        tvjumlahp.setText(b);
        updateharga(Integer.parseInt(b));

    }
    public void kurang(View v){
        jpesan = Integer.parseInt(tvjumlahp.getText().toString());
        if(jpesan == 1){
            Toast.makeText(jumlahpesanan.this, "Minimum Pesanan Adalah 1 Unit", Toast.LENGTH_SHORT).show();
        }else{
            String b = String.valueOf(jpesan-1);
            tvjumlahp.setText(b);
            updateharga(Integer.parseInt(b));
        }
    }
    private void updateharga(Integer jumlah){
        total = Integer.parseInt(hargad);
        hargatotal = total * jumlah;
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        String hargarp = kursIndonesia.format(Integer.parseInt(String.valueOf(hargatotal)));
        tvtotalp.setText(hargarp);
    }
    public void batal(View v){
        jumlahpesanan.this.finish();
    }
    public void pesan(View v){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        String uuid = user.getUid();
        mdatabase = FirebaseDatabase.getInstance().getReference();
        mdatabase.child("user").child(uuid).child("pesanan").child(tipe).child("jenis").setValue(tvjenisp.getText());
        mdatabase.child("user").child(uuid).child("pesanan").child(tipe).child("jumlah").setValue(tvjumlahp.getText());
        mdatabase.child("user").child(uuid).child("pesanan").child(tipe).child("total").setValue(String.valueOf(hargatotal));

        startActivity(new Intent(jumlahpesanan.this, Pesan.class));
        jumlahpesanan.this.finish();
    }
}