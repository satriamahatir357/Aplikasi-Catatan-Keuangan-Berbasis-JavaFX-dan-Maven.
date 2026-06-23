package backend;

import javafx.collections.FXCollections;
import java.util.Observable;
import javafx.collections.ObservableList;
import model.Transaksi;

public class SearchService {
    
    public ObservableList<Transaksi> search(
        ObservableList<Transaksi> transaksiList, //Ini adalah semua data transaksi yang ada di tabel.
        String keyword // Parameter kedua
    ) {
        ObservableList<Transaksi> hasil = FXCollections.observableArrayList(); // artinya : Saya membuat daftar kosong untuk menampung hasil pencarian.

        if(keyword == null || keyword.isBlank()){ // Kalau user belum mengetik apa pun, kita tampilkan semua data.
            return transaksiList;
        }

        keyword = keyword.toLowerCase(); // toLowerCase() = Mengubah huruf menjadi kecil.
        
        for(Transaksi transaksi : transaksiList){ // Ambil setiap transaksi satu per satu.
            // Ambil keterangan transaksi
            String keterangan = transaksi.getKeterangan().toLowerCase();

            // Cek apakah cocok
            if(keterangan.contains(keyword)){ // Mengecek apakah sebuah teks mengandung teks lain. "Makan Ayam".contains("mak") maka hasilnya: true
                hasil.add(transaksi); // Menambahkan data ke list.
            }
        }

        return hasil;
    }
    
}
