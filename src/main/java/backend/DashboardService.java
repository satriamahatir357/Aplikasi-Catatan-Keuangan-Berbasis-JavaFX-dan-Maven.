package backend;

import javafx.collections.ObservableList;
import model.Transaksi;

public class DashboardService {
    private final TransaksiService transaksiService;

    public DashboardService (TransaksiService transaksiService) {
        this.transaksiService = transaksiService;
    }

    // Method getTotalPemasukan()
    public double getTotalPemasukan(){
        double  total = 0; // Inisialisasi variabel total untuk menyimpan jumlah total pemasukan.

        for (Transaksi transaksi : transaksiService.getDaftarTransaksi()) {
            if (transaksi.getTipe().equals("Pemasukan")) {
                total += transaksi.getNominal();
            }
        }
        return total; // Mengembalikan jumlah total pemasukan yang telah dihitung.
    }

    // Method getTotalPemasukan() dengan parameter ObservableList<Transaksi> 
    // perbedaan dengan method getTotalPemasukan() sebelumnya adalah method ini menerima parameter ObservableList<Transaksi> daftar, sehingga dapat menghitung total pemasukan dari daftar transaksi yang diberikan sebagai argumen.
    public double getTotalPemasukan(ObservableList<Transaksi> daftar){
        double total = 0;

        for(Transaksi transaksi : daftar){
            if(transaksi.getTipe().equals("Pemasukan")){
                total += transaksi.getNominal();
            }
        }

        return total;
    }

    // Method getTotalPengeluaran()
    public double getTotalPengeluaran(){
        double total = 0; // Inisialisasi variabel total untuk menyimpan jumlah total pengeluaran.

        for (Transaksi transaksi : transaksiService.getDaftarTransaksi()) { // transaksiService.getDaftarTransaksi() Artinya: Ambil daftar semua transaksi dari transaksiService
            if (transaksi.getTipe().equals("Pengeluaran")) {
                total += transaksi.getNominal();
            }
        }
        return total; // Mengembalikan jumlah total pengeluaran yang telah dihitung.
    }

    // Method getTotalPengeluaran() dengan parameter ObservableList<Transaksi>
    // perbedaan dengan method getTotalPengeluaran() sebelumnya adalah method ini menerima parameter ObservableList<Transaksi> daftar, sehingga dapat menghitung total pengeluaran dari daftar transaksi yang diberikan sebagai argumen.
    public double getTotalPengeluaran(ObservableList<Transaksi> daftar){
        double total = 0;

        for(Transaksi transaksi : daftar){
            if(transaksi.getTipe().equals("Pengeluaran")){
                total += transaksi.getNominal();
            }
        }

        return total;
    }

    // Method getSaldo()
    public double getSaldo(){
        return getTotalPemasukan() - getTotalPengeluaran(); // Menghitung saldo dengan mengurangi total pengeluaran dari total pemasukan menggunakan metode getTotalPemasukan() dan getTotalPengeluaran().
    }

    public double getSaldo(ObservableList<Transaksi> daftar){
        return getTotalPemasukan(daftar) - getTotalPengeluaran(daftar); // Menghitung saldo dengan mengurangi total pengeluaran dari total pemasukan menggunakan metode getTotalPemasukan() dan getTotalPengeluaran().
    }

    public int getJumlahTransaksi(){
        return transaksiService.getDaftarTransaksi().size(); // Kenapa cukup size()? Karena getDaftarTransaksi() mengembalikan ObservableList<Transaksi>, dan size() langsung memberi jumlah item di dalam list.
    }

    public int getJumlahTransaksi(ObservableList<Transaksi> daftar){
        return daftar.size(); // Mengembalikan jumlah transaksi dalam daftar yang diberikan sebagai argumen.
    } 

    public double getRataRataNominal(){
        if(transaksiService.getDaftarTransaksi().isEmpty()){
            return 0;
        }

        double total = 0;

        // Untuk setiap transaksi yang ada di daftar transaksi, ambil nilai nominalnya lalu tambahkan ke variabel total.
        for(Transaksi transaksi : transaksiService.getDaftarTransaksi()){ // transaksiService.getDaftarTransaksi() Artinya: Ambil daftar semua transaksi dari transaksiService
            total += transaksi.getNominal();
        }

        return total / transaksiService.getDaftarTransaksi().size();
        
    }

    // Method getRataRataNominal() dengan parameter ObservableList<Transaksi> daftar
    // perbedaan dengan method getRataRataNominal() sebelumnya adalah method ini menerima parameter ObservableList<Transaksi> daftar, sehingga dapat menghitung rata-rata nominal dari daftar transaksi yang diberikan sebagai argumen.
    public double getRataRataNominal(ObservableList<Transaksi> daftar){
        if(daftar.isEmpty()){
            return 0;
        }

        double total = 0;

        for(Transaksi transaksi : daftar){
            total += transaksi.getNominal();
        }

        return total / daftar.size();
    }
}
