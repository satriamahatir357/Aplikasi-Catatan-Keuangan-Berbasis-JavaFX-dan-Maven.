package frontend.view;

import backend.TransaksiService;
import javafx.geometry.Pos; // Pos adalah sebuah kelas dalam JavaFX yang digunakan untuk menentukan posisi elemen dalam layout.
import javafx.scene.control.Label; // Label adalah sebuah kelas dalam JavaFX yang digunakan untuk menampilkan teks statis pada antarmuka pengguna. Label biasanya digunakan untuk memberikan informasi atau deskripsi kepada pengguna.
import javafx.scene.layout.HBox; // HBox adalah sebuah kelas dalam JavaFX yang digunakan untuk mengatur tata letak elemen-elemen secara horizontal. HBox memungkinkan Anda untuk menempatkan elemen-elemen di dalamnya secara berurutan dari kiri ke kanan.
import javafx.scene.layout.VBox; // VBox adalah sebuah kelas dalam JavaFX yang digunakan untuk mengatur tata letak elemen-elemen secara vertikal. VBox memungkinkan Anda untuk menempatkan elemen-elemen di dalamnya secara berurutan dari atas ke bawah.

public class DashboardView extends VBox { // DashboardView adalah sebuah kelas yang merupakan turunan dari VBox, yang digunakan untuk membuat tampilan dashboard dalam aplikasi. DashboardView akan menampilkan informasi seperti total pemasukan, total pengeluaran, dan saldo kepada pengguna.

        // field class
        private Label pemasukanValue;
        private Label pengeluaranValue;
        private Label saldoValue;
        private TransaksiService transaksiService;

    public DashboardView(TransaksiService transaksiService) {
        this.transaksiService = transaksiService;
        
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
        
        // Container untuk semua card
        HBox cardContainer = new HBox(
                pemasukanCard,
                pengeluaranCard,
                saldoCard
        );
        
        // Styling Layout
        getStyleClass().add("dashboard-view");
        
        cardContainer.getStyleClass().add("card-container");
        
        // Masukkan card ke DashboardView
        getChildren().add(cardContainer);

        // memanggil method refreshDashboard
        refreshDashboard();
    }

    // construktor
    public void updateDashboard(double pemasukan, double pengeluaran){
            double saldo = pemasukan - pengeluaran;

                pemasukanValue.setText("Rp " + pemasukan);
                pengeluaranValue.setText("Rp " + pengeluaran);
                saldoValue.setText("Rp " + saldo);
        }

    // Method refreshDashboard
    public void refreshDashboard(){
        double pemasukan = transaksiService.getTotalPemasukan();
        double pengeluaran = transaksiService.getTotalPengeluaran();

        updateDashboard(pemasukan, pengeluaran);
    }
}