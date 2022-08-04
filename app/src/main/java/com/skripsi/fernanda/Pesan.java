package com.skripsi.fernanda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

public class Pesan extends AppCompatActivity {
    private DatabaseReference mdatabase;
    private FirebaseAuth mAuth;
    String uuid;
    FrameLayout fl;
    TextView tvatas, tvk1, tvk2, tvk3, tvt1, tvt2, tvt3,tvp1,tvp2,tvp3,tvtotal, jasa;
    Integer a,b,c,d,e,nomjasa;
    private static final String TAG = "pesan";
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    private Context mContext;
    private Activity mActivity;
    private ArrayList<Model> imagesList;
    private holderbarang adminadapter = null;
    Bitmap bmp;
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
        jasa = findViewById(R.id.textView28);
        a = 0;
        b=0;
        c=0;
        d=0;
        nomjasa = 0;
        cekpesanan();
        //mulai
        mActivity = Pesan.this;
        mContext = getApplicationContext();
        FirebaseApp.initializeApp(this);
        recyclerView = findViewById(R.id.rva);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        recyclerView.setNestedScrollingEnabled(false);
        imagesList = new ArrayList<>();
        showkompor();
    }

    public void pilihkompor(View v) {
        showkompor();
    }

    public void pilihtab(View v) {
        showtabung();
    }

    public void pilihpipa(View v) {
        showpipa();
    }

    private void cekpesanan() {
        mdatabase = FirebaseDatabase.getInstance().getReference().child("user").child(uuid);
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("pesanan")) {
                    fl.setVisibility(View.VISIBLE);
                    cekkompor();

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
        mdatabase = FirebaseDatabase.getInstance(

        ).getReference().child("user").child(uuid).child("pesanan");
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("kompor")) {
                    getdatakompor();
                }
                if (dataSnapshot.hasChild("tabung")) {
                    getdatatabung();
                }
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
        mdatabase = FirebaseDatabase.getInstance().getReference().child("user").child(uuid).child("pesanan").child("kompor");
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Model post = dataSnapshot.getValue(Model.class);
                tvk1.setText(post.getJenis());
                tvk2.setText(post.getJumlah());
                DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

                formatRp.setCurrencySymbol("");
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
        mdatabase = FirebaseDatabase.getInstance().getReference().child("user").child(uuid).child("pesanan").child("tabung");
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Model post = dataSnapshot.getValue(Model.class);
                tvp1.setText(post.getJenis());
                tvp2.setText(post.getJumlah());
                DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

                formatRp.setCurrencySymbol("");
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
        mdatabase = FirebaseDatabase.getInstance().getReference().child("user").child(uuid).child("pesanan").child("pipa");
        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Model post = dataSnapshot.getValue(Model.class);
                tvt1.setText(post.getJenis());
                tvt2.setText(post.getJumlah());
                DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

                formatRp.setCurrencySymbol("");
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
            d = a+b+c;
            e = d*30/100;
            nomjasa = e+d;

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);
        String hargarp = kursIndonesia.format(Integer.parseInt(String.valueOf(nomjasa)));
        String jasarp = kursIndonesia.format(Integer.parseInt(String.valueOf(e)));
        tvtotal.setText(hargarp);
        jasa.setText(jasarp);
    }

    private void showkompor(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("barang").child("kompor");
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
    private void showtabung(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("barang").child("tabung");
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

    private void showpipa(){
        databaseReference = FirebaseDatabase.getInstance().getReference().child("barang").child("pipa");
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



    public void share ( View v){
        //Bitmap image = getBitmapFromView(fl);
        //shareBitmap(image);
        String a = tvk1.getText().toString()+"\n"+tvk2.getText().toString()+"   "+tvk3.getText().toString();
        String b = tvp1.getText().toString()+"\n"+tvp2.getText().toString()+"   "+tvp3.getText().toString();
        String c = tvt1.getText().toString()+"\n"+tvt2.getText().toString()+"   "+tvt3.getText().toString();
        String d = "Jasa Instalasi"+"   "+jasa.getText().toString();
        String e = "Total"+"   "+ tvtotal.getText().toString();
        String f = "Dapatkah Saya mendapat penawaran yang lebih menarik dari Pandu Jaya Teknik ?";
        BigInteger nope = new BigInteger("+62895346291925");
        String url = "https://api.whatsapp.com/send?phone="+nope+ "&text= Halo\nSaya berniat memesan :"+"\n\n"+a+"\n"+b+"\n"+c+"\n"+d+"\n"+e+"\n\n"+f;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);


    }
    private Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            bgDrawable.draw(canvas);
        }   else{
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void shareBitmap(@NonNull Bitmap bitmap)
    {
        File cachePath = new File(getExternalCacheDir(), "my_images/");
        cachePath.mkdirs();

        File file = new File(cachePath, "Image_123.png");
        FileOutputStream fileOutputStream;
        try
        {
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }


        Uri myImageFileUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);

        Intent intent = new Intent(Intent.ACTION_SEND);
        //intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"user@example.com"});
        intent.putExtra(Intent.EXTRA_TEXT, "https://api.whatsapp.com/send?phone=+62895346291925&text= Halo\nSaya berniat membeli");
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_STREAM,myImageFileUri);
        intent.setType("image/jpeg");

        intent.setPackage("com.whatsapp");
        startActivity(intent);
    }


}

