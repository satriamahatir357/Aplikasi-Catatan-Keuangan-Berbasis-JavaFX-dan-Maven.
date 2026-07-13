package backend;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Transaksi;
import java.time.LocalDate;

public class FilterService {
    public ObservableList<Transaksi> filter(
        ObservableList<Transaksi> daftar,
        String tipe
    ){
        if(tipe.equals("Semua")){
            return daftar;
        }

        ObservableList<Transaksi> hasil =
            FXCollections.observableArrayList();

        for(Transaksi transaksi : daftar){
            if (transaksi.getTipe().equalsIgnoreCase(tipe)){
                hasil.add(transaksi);
            }

        }
        return hasil;
    }

    // == filter berdasarkan period == 
    // ini method filterPeriode() digunakan untuk memfilter daftar transaksi berdasarkan periode tertentu.
    // Parameter ObservableList<Transaksi> daftar: Ini adalah daftar transaksi yang akan difilter
    public ObservableList<Transaksi> filterPeriode(
        ObservableList<Transaksi> daftar,
        String periode
    ) {
        if(periode.equals("Semua")){
            return daftar; // Jika periode yang diberikan adalah "Semua", maka method akan mengembalikan daftar transaksi tanpa melakukan filter.
        }

        ObservableList<Transaksi> hasil =  // Inisialisasi daftar hasil filter sebagai ObservableList kosong menggunakan FXCollections.observableArrayList().
            FXCollections.observableArrayList();

        LocalDate hariIni = LocalDate.now(); // Mengambil tanggal saat ini menggunakan LocalDate.now() dan menyimpannya dalam variabel hariIni.

        for(Transaksi transaksi : daftar){
            LocalDate tanggalTransaksi = LocalDate.parse(transaksi.getTanggal()); // Mengambil tanggal transaksi dari objek Transaksi dan mengubahnya menjadi objek LocalDate menggunakan LocalDate.parse().
        
            if(periode.equals("Hari ini")){ // Jika periode yang diberikan adalah "Hari ini", maka method akan memeriksa apakah tanggal transaksi sama dengan tanggal saat ini.
                if(tanggalTransaksi.equals(hariIni)){
                    hasil.add(transaksi);
                }
            }

            else if(periode.equals("7 Hari")){ // Jika periode yang diberikan adalah "7 Hari", maka method akan memeriksa apakah tanggal transaksi berada dalam rentang 7 hari terakhir (termasuk hari ini).
                if(!tanggalTransaksi.isBefore(hariIni.minusDays(6)) // Menggunakan metode isBefore() untuk memeriksa apakah tanggal transaksi tidak sebelum 6 hari yang lalu dari hari ini
                    && !tanggalTransaksi.isAfter(hariIni)){ //  isAfter() untuk memeriksa apakah tanggal transaksi tidak setelah hari ini. 

                    hasil.add(transaksi);
                } 
            }

            else if(periode.equals("30 Hari")){
                if(!tanggalTransaksi.isBefore(hariIni.minusDays(29))
                    && !tanggalTransaksi.isAfter(hariIni)){
                    
                    hasil.add(transaksi);
                }
            }

            else if(periode.equals("Bulan ini")){
                if(tanggalTransaksi.getMonth() == hariIni.getMonth()
                    && tanggalTransaksi.getYear() == hariIni.getYear()){
                    
                    hasil.add(transaksi);
                }
            }

            else if(periode.equals("Tahun ini")){
                if(tanggalTransaksi.getYear() == hariIni.getYear()){
                    
                    hasil.add(transaksi);
                }
            }
        }
        
        return hasil;
    }
}
