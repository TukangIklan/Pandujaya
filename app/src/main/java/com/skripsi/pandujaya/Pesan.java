package com.skripsi.pandujaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class Pesan extends AppCompatActivity {
    private DatabaseReference mdatabase;
    private FirebaseAuth mAuth;
    String uuid;
    LinearLayout ll;
    TextView tvatas, tvk1, tvk2, tvk3, tvt1, tvt2, tvt3,tvp1,tvp2,tvp3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        uuid = user.getUid();
        ll = findViewById(R.id.ll);
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
                    ll.setVisibility(View.VISIBLE);
                    cekkompor();
                    cekpipa();
                    cektabung();
                } else {
                    ll.setVisibility(View.GONE);
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
                tvk3.setText(post.getTotal());
                Toast.makeText(Pesan.this, post.getJenis(), Toast.LENGTH_SHORT).show();
                // ..
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
                tvp3.setText(post.getTotal());
                Toast.makeText(Pesan.this, post.getJenis(), Toast.LENGTH_SHORT).show();
                // ..
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
                tvt3.setText(post.getTotal());
                Toast.makeText(Pesan.this, post.getJenis(), Toast.LENGTH_SHORT).show();
                // ..
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("aaaaa", "loadPost:onCancelled", databaseError.toException());
            }
        });

    }

}
