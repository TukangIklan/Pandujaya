package com.skripsi.fernanda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Resetpass extends AppCompatActivity {
    EditText email;
    String buatcekemail;
    boolean bolmail;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpass);
        buatcekemail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        email = findViewById(R.id.etmaill);
    }
    public void proseslupa(View v){
        cekemail();
        if (bolmail){
            lupapassword();
        }
    }
    private void cekemail() {
        if (email.getText().length() < 1) {
            email.setError("Email wajib diisi");
        } else {
            if (email.getText().toString().trim().matches(buatcekemail)) {
                bolmail = true;
            } else {
                email.setError("Alamat Email tidak valid");
            }
        }
    }
    private void lupapassword(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.setLanguageCode("id");
        auth.sendPasswordResetEmail(email.getText().toString()).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                new AlertDialog.Builder(Resetpass.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setCancelable(false)
                        .setMessage("Silahkan Periksa Email Anda untuk mengubah password")
                        .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Resetpass.this.finish();
                                startActivity(new Intent(Resetpass.this, Login.class));
                            }
                        })
                        .show();
            }
            else {
                new AlertDialog.Builder(Resetpass.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setCancelable(false)
                        .setMessage("Email Yang anda Berika Belum terdaftar")
                        .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Resetpass.this.finish();
                            }
                        })
                        .show();
            }
        });
    }
}