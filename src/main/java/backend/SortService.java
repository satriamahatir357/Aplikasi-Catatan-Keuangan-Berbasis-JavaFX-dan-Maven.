package backend;

import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Transaksi;

public class SortService {
    public ObservableList<Transaksi> sort(
        ObservableList<Transaksi> daftar,
        String pilihan
    ){
        ObservableList<Transaksi> hasil =
            FXCollections.observableArrayList(daftar);

        switch (pilihan) {
            case "Terbaru":
                // Urutkan daftar hasil berdasarkan tanggal transaksi, lalu balik urutannya sehingga transaksi dengan tanggal paling baru berada di urutan pertama
                hasil.sort( //Urutkan isi hasil.
                    Comparator.comparing( // Tentukan data apa yang akan dijadikan dasar pengurutan.
                            Transaksi::getTanggal)  // Untuk setiap objek Transaksi, ambil nilai getTanggal().
                            .reversed() // Balik urutannya.
                );
                break;

           case "Terlama":
                hasil.sort(
                        Comparator.comparing(Transaksi::getTanggal)
                );
                break;
            
            case "Nominal Terbesar":
                hasil.sort(
                    Comparator.comparing(Transaksi::getNominal) 
                            .reversed()
                );
                break;
            
            case "Nominal Terkecil":
                hasil.sort(
                    Comparator.comparing(Transaksi::getNominal)
                );
                break;
            
            case "A-Z":
                hasil.sort(
                    Comparator.comparing(Transaksi::getKeterangan) 
                );
                break;
            
            case "Z-A":
                hasil.sort(
                    Comparator.comparing(Transaksi::getKeterangan) 
                            .reversed()
                );
                break;
        
            default:
                break;
        }

        return hasil;
    }
}
