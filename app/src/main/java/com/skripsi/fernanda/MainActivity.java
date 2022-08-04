package com.skripsi.fernanda;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    ProgressDialog pd;
    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }
    public void ceklogin(View v){
        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Memeriksa User ...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();
        FirebaseUser sudahlogin = mAuth.getCurrentUser();
        if (sudahlogin!=null ){
            pd.dismiss();
            startActivity(new Intent(MainActivity.this, Pesan.class));
        }else{
            pd.dismiss();
            startActivity(new Intent(MainActivity.this, Signup.class));
        }

    }
}