package backend;
// Fungsi TransaksiService = Kalau Transaksi.java adalah bentuk data seperti keterangan, nominal, tipe, tanggal.
// maka TransaksiService bertugas:: unntuk menyimpan transaksi, menambah transaksi, menghapus transaksi, menghitung pemasukkan, menhitung pengeluaran, dan menghitung saldo.

import javafx.collections.FXCollections; // FXCollections adalah sebuah kelas utilitas yang menyediakan metode untuk membuat dan mengelola ObservableList, yang merupakan jenis list yang dapat diamati (observable) dalam JavaFX.
import javafx.collections.ObservableList; // ObservableList adalah sebuah interface yang digunakan untuk menyimpan data dalam bentuk list yang dapat diamati (observable).

import model.Transaksi;
import storage.CSVHelper;
import storage.CSVHelper; // Mengimpor class CSVHelper yang berada di package storage, supaya bisa digunakan langsung di file Java ini

// Tempat Penyimpanan Data
public class TransaksiService {
    public ObservableList<Transaksi> daftarTransaksi = // ObservableList digunakan untuk menyimpan data transaksi yang dapat diamati (observable) dalam JavaFX.
            FXCollections.observableArrayList(); // observableArrayList() adalah metode statis yang digunakan untuk membuat sebuah ObservableList baru yang dapat diamati (observable) dalam JavaFX.

    public TransaksiService() {
            daftarTransaksi.addAll(
                CSVHelper.loadFromCSV() // Ambil semua data transaksi dari file CSV, lalu tambahkan semuanya ke daftarTransaksi.
            );
    }

    public ObservableList<Transaksi> getDaftarTransaksi(){ // getDaftarTransaksi() adalah sebuah metode yang digunakan untuk mengambil data transaksi yang disimpan dalam daftarTransaksi.
        return daftarTransaksi; // Mengembalikan daftar transaksi yang disimpan dalam ObservableList.
    }

    // Method tambahTransaksi()
    public void tambahTransaksi(Transaksi transaksi){ // tambahTransaksi() adalah sebuah metode yang digunakan untuk menambahkan transaksi baru ke dalam daftarTransaksi.
        daftarTransaksi.add(transaksi); // Menambahkan transaksi baru ke dalam daftarTransaksi menggunakan metode add() dari ObservableList.
        
        CSVHelper.saveToCSV(daftarTransaksi); // Artinya: Tambah transaksi -> Masuk ObservableList -> Simpan ke CSV
    }

    // Method hapusTransaksi()
    public void hapusTransaksi(Transaksi transaksi){ // hapusTransaksi() adalah sebuah metode yang digunakan untuk menghapus transaksi dari daftarTransaksi.
        daftarTransaksi.remove(transaksi); // Menghapus transaksi dari daftarTransaksi menggunakan metode remove() dari ObservableList.
    
        CSVHelper.saveToCSV(daftarTransaksi); // Artinya: hapus transaksi -> update ObservableList -> Tulis ulang CSV
    }

    public void updateTransaksi() {

        CSVHelper.saveToCSV(daftarTransaksi);

    }

}
