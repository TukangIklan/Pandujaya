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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    EditText etnama, etmail, etpass;

    String buatcekemail;
    CheckBox checkBox;
    boolean bolmail, bolnama, bolpass, bolhp;
    ProgressDialog pd;
    String username;
    private FirebaseAuth mAuth;
    private DatabaseReference mdatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();
        etnama = findViewById(R.id.etnama);
        etmail = findViewById(R.id.etmail);
        etpass = findViewById(R.id.etpass);
        checkBox = findViewById(R.id.checkBox);
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
    public void prosesdaftar(View v) {
        ceknama();
        cekemail();
        cekpass();
        if (bolnama && bolmail && bolpass) {
            pd = new ProgressDialog(Signup.this);
            pd.setMessage("Mendaftarkan ...");
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.show();
            signup();
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

    private void ceknama() {
        if (etnama.getText().length() < 1) {
            etnama.setError("Nama harus diisi");
        } else {
            bolnama = true;
        }
    }

    private void signup() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(etmail.getText().toString(), etpass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            pd.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            String uuid = user.getUid();
                            mdatabase = FirebaseDatabase.getInstance().getReference();
                            mdatabase.child("user").child(uuid).child("username").setValue(etnama.getText().toString());
                            Signup.this.finish();
                            startActivity(new Intent(Signup.this, Pesan.class));
                        } else {
                            pd.dismiss();
                            new AlertDialog.Builder(Signup.this)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setCancelable(false)
                                    .setTitle("Maaf !")
                                    .setMessage("Pendaftaran Gagal, Mohon Periksa Data Yang Anda Berikan")
                                    .setPositiveButton("OKE", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }
                    }
                });
    }
    public void kelogin(View v) {
        startActivity(new Intent(Signup.this, Login.class));
        Signup.this.finish();
    }

}