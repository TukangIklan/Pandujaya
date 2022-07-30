package com.skripsi.pandujaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Pesan extends AppCompatActivity {
    private DatabaseReference mdatabase;
    private FirebaseAuth mAuth;
    String uuid;
    FrameLayout fl;
    TextView tvatas, tvk1, tvk2, tvk3, tvt1, tvt2, tvt3,tvp1,tvp2,tvp3,tvtotal;
    Integer a,b,c,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        uuid = user.getUid();
        fl = findViewById(R.id.fl);
        tvk1 = findViewById(R.id.tvk1);
        tvk2 = findViewById(R.id.tvk2);
        tvk3 = findViewById(R.id.tvk3);
        tvp1 = findViewById(R.id.textView29);
        tvp2 = findViewById(R.id.textView30);
        tvp3 = findViewById(R.id.textView31);
        tvt1 = findViewById(R.id.textView32);
        tvt2 = findViewById(R.id.textView33);
        tvt3 = findViewById(R.id.textView34);
        tvatas = findViewById(R.id.textView12);
        tvtotal = findViewById(R.id.textView19);
        a = 0;
        b=0;
        c=0;
        d=0;
        cekpesanan();
    }

    public void pilihkompor(View v) {
        this.finish();
        startActivity(new Intent(Pesan.this, Pilihkompor.class));
    }

    public void pilihtab(View v) {
        this.finish();
        startActivity(new Intent(Pesan.this, Pilihtabung.class));
    }

    public void pilihpipa(View v) {
        this.finish();
        startActivity(new Intent(Pesan.this, Pilihpipa.class));
    }

    private void cekpesanan() {
        mdatabase = FirebaseDatabase.getInstance("https://ramore-skripsi-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("user").child(uuid);
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("pesanan")) {
                    fl.setVisibility(View.VISIBLE);
                    cekkompor();
                    cekpipa();
                    cektabung();
                } else {
                    fl.setVisibility(View.GONE);
                    tvatas.setText("Keranjang Pesanan Anda Kosong");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    private void cekkompor() {
        mdatabase = FirebaseDatabase.getInstance("https://ramore-skripsi-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("user").child(uuid).child("pesanan");
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("kompor")) {
                    getdatakompor();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    private void cektabung() {
        mdatabase = FirebaseDatabase.getInstance("https://ramore-skripsi-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("user").child(uuid).child("pesanan");
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("tabung")) {
                    getdatatabung();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    private void cekpipa() {
        mdatabase = FirebaseDatabase.getInstance("https://ramore-skripsi-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("user").child(uuid).child("pesanan");
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("pipa")) {
                    getdatapipa();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    private void getdatakompor() {
        mdatabase = FirebaseDatabase.getInstance("https://ramore-skripsi-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("user").child(uuid).child("pesanan").child("kompor");
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Model post = dataSnapshot.getValue(Model.class);
                tvk1.setText(post.getJenis());
                tvk2.setText(post.getJumlah());
                DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

                formatRp.setCurrencySymbol("Rp ");
                formatRp.setMonetaryDecimalSeparator(',');
                formatRp.setGroupingSeparator('.');

                kursIndonesia.setDecimalFormatSymbols(formatRp);
                String hargarp = kursIndonesia.format(Integer.parseInt(String.valueOf(post.getTotal())));
                tvk3.setText(hargarp);
                a = Integer.parseInt(post.getTotal());
                updateharga();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("aaaaa", "loadPost:onCancelled", databaseError.toException());
            }
        });

    }
    private void getdatatabung() {
        mdatabase = FirebaseDatabase.getInstance("https://ramore-skripsi-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("user").child(uuid).child("pesanan").child("tabung");
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Model post = dataSnapshot.getValue(Model.class);
                tvp1.setText(post.getJenis());
                tvp2.setText(post.getJumlah());
                DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

                formatRp.setCurrencySymbol("Rp ");
                formatRp.setMonetaryDecimalSeparator(',');
                formatRp.setGroupingSeparator('.');

                kursIndonesia.setDecimalFormatSymbols(formatRp);
                String hargarp = kursIndonesia.format(Integer.parseInt(String.valueOf(post.getTotal())));
                tvp3.setText(hargarp );
                b = Integer.parseInt(post.getTotal());
                updateharga();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("aaaaa", "loadPost:onCancelled", databaseError.toException());
            }
        });

    }
    private void getdatapipa() {
        mdatabase = FirebaseDatabase.getInstance("https://ramore-skripsi-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("user").child(uuid).child("pesanan").child("pipa");
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Model post = dataSnapshot.getValue(Model.class);
                tvt1.setText(post.getJenis());
                tvt2.setText(post.getJumlah());
                DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

                formatRp.setCurrencySymbol("Rp ");
                formatRp.setMonetaryDecimalSeparator(',');
                formatRp.setGroupingSeparator('.');

                kursIndonesia.setDecimalFormatSymbols(formatRp);
                String hargarp = kursIndonesia.format(Integer.parseInt(String.valueOf(post.getTotal())));
                tvt3.setText(hargarp );
                c = Integer.parseInt(post.getTotal());
                updateharga();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("aaaaa", "loadPost:onCancelled", databaseError.toException());
            }
        });

    }
    private void updateharga(){
        if(a !=0){
            d = a;
        }
        if(a !=0 && b !=0){
            d = a+b;
        }
        if(a !=0 && b !=0 && c !=0){
            d = a+b+c;
        }

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        String hargarp = kursIndonesia.format(Integer.parseInt(String.valueOf(d)));
        tvtotal.setText(hargarp);
    }

}
