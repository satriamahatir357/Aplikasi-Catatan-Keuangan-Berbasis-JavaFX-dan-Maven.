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
        return getTotalPemasukan(transaksiService.getDaftarTransaksi());
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
        return getTotalPengeluaran(transaksiService.getDaftarTransaksi());
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
        return getSaldo(transaksiService.getDaftarTransaksi());
    }

    public double getSaldo(ObservableList<Transaksi> daftar){
        return getTotalPemasukan(daftar) - getTotalPengeluaran(daftar); // Menghitung saldo dengan mengurangi total pengeluaran dari total pemasukan menggunakan metode getTotalPemasukan() dan getTotalPengeluaran().
    }

    public int getJumlahTransaksi(){
        return getJumlahTransaksi(transaksiService.getDaftarTransaksi());
    }

    public int getJumlahTransaksi(ObservableList<Transaksi> daftar){
        return daftar.size(); // Mengembalikan jumlah transaksi dalam daftar yang diberikan sebagai argumen.
    } 

    public double getRataRataNominal(){
        return getRataRataNominal(transaksiService.getDaftarTransaksi());
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
