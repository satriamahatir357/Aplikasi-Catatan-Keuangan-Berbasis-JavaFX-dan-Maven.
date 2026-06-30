package frontend.view;

import backend.DashboardService;
import backend.TransaksiService;
import javafx.geometry.Pos; // Pos adalah sebuah kelas dalam JavaFX yang digunakan untuk menentukan posisi elemen dalam layout.
import javafx.scene.control.Label; // Label adalah sebuah kelas dalam JavaFX yang digunakan untuk menampilkan teks statis pada antarmuka pengguna. Label biasanya digunakan untuk memberikan informasi atau deskripsi kepada pengguna.
import javafx.scene.layout.HBox; // HBox adalah sebuah kelas dalam JavaFX yang digunakan untuk mengatur tata letak elemen-elemen secara horizontal. HBox memungkinkan Anda untuk menempatkan elemen-elemen di dalamnya secara berurutan dari kiri ke kanan.
import javafx.scene.layout.VBox; // VBox adalah sebuah kelas dalam JavaFX yang digunakan untuk mengatur tata letak elemen-elemen secara vertikal. VBox memungkinkan Anda untuk menempatkan elemen-elemen di dalamnya secara berurutan dari atas ke bawah.

import java.text.NumberFormat;
import java.util.Locale;

public class DashboardView extends VBox { // DashboardView adalah sebuah kelas yang merupakan turunan dari VBox, yang digunakan untuk membuat tampilan dashboard dalam aplikasi. DashboardView akan menampilkan informasi seperti total pemasukan, total pengeluaran, dan saldo kepada pengguna.

        // field class
        private Label pemasukanValue;
        private Label pengeluaranValue;
        private Label saldoValue;
        private TransaksiService transaksiService;
        private final DashboardService dashboardService;
        private Label jumlahTransaksiValue;
        private Label rataRataValue;

    public DashboardView(TransaksiService transaksiService) {
        this.transaksiService = transaksiService;
        this.dashboardService = new DashboardService(transaksiService); // DashboardService memakai TransaksiService sebagai sumber data.
        
        // Card Pemasukan
        Label pemasukanTitle = new Label("Total Pemasukan");
        pemasukanTitle.getStyleClass().add("card-title");

        pemasukanValue = new Label("Rp 0");
        pemasukanValue.getStyleClass().add("card-value");

        VBox pemasukanCard = new VBox(
                pemasukanTitle,
                pemasukanValue
        );
        pemasukanCard.getStyleClass().add("dashboard-card");
        pemasukanCard.getStyleClass().add("income-card");
        
        // Card pengeluaran
        Label pengeluaranTitle = new Label("Total Pengeluaran");
        pengeluaranTitle.getStyleClass().add("card-title");
        
        pengeluaranValue = new Label("Rp 0");
        pengeluaranValue.getStyleClass().add("card-value");
        
        VBox pengeluaranCard = new VBox(
                pengeluaranTitle,
                pengeluaranValue
        );
        pengeluaranCard.getStyleClass().add("dashboard-card");
        pengeluaranCard.getStyleClass().add("expense-card");
        
        // Card Saldo
        Label saldoTitle = new Label("Saldo");
        saldoTitle.getStyleClass().add("card-title");
        
        saldoValue = new Label("Rp 0");
        saldoValue.getStyleClass().add("card-value");
        
        VBox saldoCard = new VBox(
                saldoTitle,
                saldoValue
        );
        saldoCard.getStyleClass().add("dashboard-card");
        saldoCard.getStyleClass().add("balance-card");

        // Card jumlah transaksi
        Label jumlahTitle = new Label("Jumlah Transaksi");
        jumlahTitle.getStyleClass().add("card-title");

        jumlahTransaksiValue = new Label("0");
        jumlahTransaksiValue.getStyleClass().add("card-value");

        VBox jumlahCard = new VBox(
            jumlahTitle,
            jumlahTransaksiValue
        );

        jumlahCard.getStyleClass().add("dashboard-card-row2");
        jumlahCard.getStyleClass().add("transaction-card");

        // Card Rata-rata nominal
        Label rataTitle = new Label("Rata-rata Nominal");
        rataTitle.getStyleClass().add("card-title");

        rataRataValue = new Label("Rp 0");
        rataRataValue.getStyleClass().add("card-value");

        VBox rataCard = new VBox(
            rataTitle,
            rataRataValue
        );

        rataCard.getStyleClass().add("dashboard-card-row2");
        rataCard.getStyleClass().add("avenger-card");
        
        // Container untuk semua card
        HBox row1 = new HBox(
                pemasukanCard,
                pengeluaranCard,
                saldoCard
        );
        row1.getStyleClass().add("card-container");
        
        HBox row2 = new HBox(
            jumlahCard,
            rataCard
        );
        row2.getStyleClass().add("card-container");

        // menyatukkan row card dalam satu VBox
        VBox dashboardCards = new VBox(
            row1,
            row2
        );
        dashboardCards.setSpacing(25);
        
        // Styling Layout
        getStyleClass().add("dashboard-view");
        
        dashboardCards.getStyleClass().add("card-container");
        
        // Masukkan card ke DashboardView
        getChildren().add(dashboardCards);

        // memanggil method refreshDashboard
        refreshDashboard();
    }

    // construktor
    public void updateDashboard(double pemasukan, double pengeluaran, double rataRata){
            double saldo = pemasukan - pengeluaran;

             // Formatter mata uang Rupiah Indonesia
            NumberFormat rupiah =
                NumberFormat.getCurrencyInstance( // getCurrencyInstance() adalah method dari class NumberFormat yang digunakan untuk membuat formatter mata uang.
                new Locale("id", "ID")
            );

            // Tampilkan data ke dashboard dalam format Rupiah
            pemasukanValue.setText(rupiah.format(pemasukan));
            pengeluaranValue.setText(rupiah.format(pengeluaran));
            saldoValue.setText(rupiah.format(saldo));
            rataRataValue.setText(rupiah.format(rataRata));
        }

    // Method refreshDashboard
    public void refreshDashboard(){
        double pemasukan = dashboardService.getTotalPemasukan();
        double pengeluaran = dashboardService.getTotalPengeluaran();
        double rataRata = dashboardService.getRataRataNominal();

        updateDashboard(pemasukan, pengeluaran, rataRata);
        
        jumlahTransaksiValue.setText(
            dashboardService.getJumlahTransaksi() + " Transaksi"
        );
    }
}