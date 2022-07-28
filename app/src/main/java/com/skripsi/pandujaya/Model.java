package com.skripsi.pandujaya;

public class Model {
    public String gambar;
    public String jenis;
    public String harga;
    public String deskripsi;
    public String jumlah;
    public String total;

    public Model(){

    }
    public Model(String gambar, String jenis, String harga, String deskripsi, String jumlah, String total) {
        this.jenis = jenis;
        this.gambar = gambar;
        this.harga = harga;
        this.deskripsi = deskripsi;
        this.jumlah = jumlah;
        this.total = total;
    }

    public String getGambar() {
        return gambar;
    }

    public String getJenis() {
        return jenis;
    }

    public String getHarga() {
        return harga;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getJumlah() {
        return jumlah;
    }

    public String getTotal() {
        return total;
    }
}
