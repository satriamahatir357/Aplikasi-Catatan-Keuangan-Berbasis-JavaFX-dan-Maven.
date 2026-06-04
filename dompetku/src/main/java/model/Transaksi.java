package model;

public class Transaksi {
    
    private String keterangan;  // Keterangan transaksi
    private double nominal; // Nominal transaksi
    private String tipe;  // Tipe transaksi (misalnya: "Pemasukan" atau "Pengeluaran")  
    private String tanggal; // Tanggal transaksi

    // Constructor
    public Transaksi(String keterangan, double nominal, String tipe, String tanggal){
        this.keterangan = keterangan;
        this.nominal = nominal;
        this.tipe = tipe;
        this.tanggal = tanggal;
    }

    // Getter
    // Getter digunakan untuk mengambil/membaca nilai dari attribute yang private

    public String getKeterangan(){
        return keterangan;
    }

    public double getNominal(){
        return nominal;
    }

    public String getTipe(){
        return tipe;
    }

    public String getTanggal(){
        return tanggal;
    }
    
}
