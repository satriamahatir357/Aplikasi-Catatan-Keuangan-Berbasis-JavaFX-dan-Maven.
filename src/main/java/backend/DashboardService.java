package backend;

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

    // Method getTotalPengeluaran()
    public double getTotalPengeluaran(){
        double total = 0; // Inisialisasi variabel total untuk menyimpan jumlah total pengeluaran.

        for (Transaksi transaksi : transaksiService.getDaftarTransaksi()) {
            if (transaksi.getTipe().equals("Pengeluaran")) {
                total += transaksi.getNominal();
            }
        }
        return total; // Mengembalikan jumlah total pengeluaran yang telah dihitung.
    }

    // Method getSaldo()
    public double getSaldo(){
        return getTotalPemasukan() - getTotalPengeluaran(); // Menghitung saldo dengan mengurangi total pengeluaran dari total pemasukan menggunakan metode getTotalPemasukan() dan getTotalPengeluaran().
    }

    public int getJumlahTransaksi(){
        return transaksiService.getDaftarTransaksi().size(); // Kenapa cukup size()? Karena getDaftarTransaksi() mengembalikan ObservableList<Transaksi>, dan size() langsung memberi jumlah item di dalam list.
    }
    }
