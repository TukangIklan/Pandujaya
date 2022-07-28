package com.skripsi.pandujaya;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Pilihtabung extends AppCompatActivity {
    private static final String TAG = "tab";
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    private Context mContext;
    private Activity mActivity;
    private ArrayList<Model> imagesList;
    private holderbarang adminadapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihtabung);

        mActivity = Pilihtabung.this;
        mContext = getApplicationContext();
        FirebaseApp.initializeApp(this);
        recyclerView = findViewById(R.id.rvt);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setNestedScrollingEnabled(false);
        imagesList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance("https://ramore-skripsi-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("barang").child("tabung");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imagesList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Model imagemodel = dataSnapshot.getValue(Model.class);

                    imagesList.add(imagemodel);
                }
                adminadapter = new holderbarang(mContext, mActivity, (ArrayList<Model>) imagesList);
                recyclerView.setAdapter(adminadapter);
                adminadapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}