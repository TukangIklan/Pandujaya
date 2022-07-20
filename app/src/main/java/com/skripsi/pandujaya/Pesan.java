package com.skripsi.pandujaya;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.math.BigInteger;

public class Pesan extends AppCompatActivity {
    EditText etkompor,etjarak,ettabung;
    String nope,kompor,jarak,tabung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);
        etkompor = findViewById(R.id.etjmlkompor);
        etjarak = findViewById(R.id.etjmljarak);
        ettabung = findViewById(R.id.etjmltabung);
// TODO: 7/21/2022 peasan kasih detail, penambahan bahan sesuai dengan var lain.... harga

    }
    public void pesan(View v){
        // TODO: 7/21/2022 masuk ke database biar di followup dari admin.... mungkin sama kaya punya andra
        kompor = etkompor.getText().toString().trim();
        jarak = etjarak.getText().toString().trim();
        tabung = ettabung.getText().toString().trim();
        Log.d("tabung", tabung);
        nope= "+62895346291925";
        BigInteger nopenya = new BigInteger(nope);
        String url1 = "https://api.whatsapp.com/send?phone=" + nopenya + "&text=Halo, Saya ingin membuat Instalasi Gas, dengan data sebagai Berikut:"+"\nJumlah Kompor :"+kompor+
                "\nJumlah Tabung :"+tabung+"\nJarak kompor ke Tabung Gas :"+ jarak+"\nDapatkah saya mendapat Penawaran dari CV. Pandu Jaya?";
        Intent ii = new Intent(Intent.ACTION_VIEW);
        ii.setData(Uri.parse(url1));
        startActivity(ii);
    }
}