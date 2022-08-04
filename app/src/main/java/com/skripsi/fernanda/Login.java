package com.skripsi.fernanda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText etmail, etpass;
    String buatcekemail;
    CheckBox checkBox;
    boolean bolmail, bolpass;
    ProgressDialog pd;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        etmail = findViewById(R.id.etemailm);
        etpass = findViewById(R.id.etpassm);
        checkBox = findViewById(R.id.checkBox2);
        buatcekemail = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    etpass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    etpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

    }
    public void proseslogin(View v) {
        cekemail();
        cekpass();
        if (bolmail && bolpass) {
            pd = new ProgressDialog(Login.this);
            pd.setMessage("Login ...");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.show();
            prosesmasuk();
        } else {
            Log.d("Lognya","masih ada error");
        }
    }
    private void cekemail() {
        if (etmail.getText().length() < 1) {
            etmail.setError("Email wajib diisi");
        } else {
            if (etmail.getText().toString().trim().matches(buatcekemail)) {
                bolmail = true;
            } else {
                etmail.setError("Alamat Email tidak valid");
            }
        }
    }
    private void cekpass() {
        if (etpass.getText().length() < 6) {
            etpass.setError("Password Minimal 6 Karakter");
        } else {
            bolpass = true;
        }
    }
    private void prosesmasuk(){
        mAuth.signInWithEmailAndPassword(etmail.getText().toString(), etpass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Login.this.finish();
                            startActivity(new Intent(Login.this, Pesan.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            new AlertDialog.Builder(Login.this)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setCancelable(false)
                                    .setTitle("Maaf !")
                                    .setMessage("Gagal Masuk, Mohon Periksa Data Yang Anda Berikan")
                                    .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }

                        // ...
                    }
                });
    }
    public void kemasuk(View v) {
        startActivity(new Intent(Login.this, Signup.class));
        Login.this.finish();
    }
    public void kereset(View v) {
        startActivity(new Intent(Login.this, Resetpass.class));
        Login.this.finish();
    }
}