package frontend.view;

import javafx.geometry.Pos; // Pos adalah sebuah kelas dalam JavaFX yang digunakan untuk menentukan posisi elemen dalam layout.
import javafx.scene.control.Label; // Label adalah sebuah kelas dalam JavaFX yang digunakan untuk menampilkan teks statis pada antarmuka pengguna. Label biasanya digunakan untuk memberikan informasi atau deskripsi kepada pengguna.
import javafx.scene.layout.HBox; // HBox adalah sebuah kelas dalam JavaFX yang digunakan untuk mengatur tata letak elemen-elemen secara horizontal. HBox memungkinkan Anda untuk menempatkan elemen-elemen di dalamnya secara berurutan dari kiri ke kanan.
import javafx.scene.layout.VBox; // VBox adalah sebuah kelas dalam JavaFX yang digunakan untuk mengatur tata letak elemen-elemen secara vertikal. VBox memungkinkan Anda untuk menempatkan elemen-elemen di dalamnya secara berurutan dari atas ke bawah.

public class DashboardView extends VBox { // DashboardView adalah sebuah kelas yang merupakan turunan dari VBox, yang digunakan untuk membuat tampilan dashboard dalam aplikasi. DashboardView akan menampilkan informasi seperti total pemasukan, total pengeluaran, dan saldo kepada pengguna.

    public DashboardView() {

        // Card Pemasukan
        Label pemasukanTitle = new Label("Total Pemasukan");
        Label pemasukanValue = new Label("Rp 0");

        VBox pemasukanCard = new VBox(
                pemasukanTitle,
                pemasukanValue
        );

        // Card Pengeluaran
        Label pengeluaranTitle = new Label("Total Pengeluaran");
        Label pengeluaranValue = new Label("Rp 0");

        VBox pengeluaranCard = new VBox(
                pengeluaranTitle,
                pengeluaranValue
        );

        // Card Saldo
        Label saldoTitle = new Label("Saldo");
        Label saldoValue = new Label("Rp 0");

        VBox saldoCard = new VBox(
                saldoTitle,
                saldoValue
        );

        // Container untuk semua card
        HBox cardContainer = new HBox(
                pemasukanCard,
                pengeluaranCard,
                saldoCard
        );

        // Styling Layout
        setSpacing(20);
        setAlignment(Pos.CENTER);

        cardContainer.setSpacing(20);
        cardContainer.setAlignment(Pos.CENTER);

        // Masukkan card ke DashboardView
        getChildren().add(cardContainer);
    }
}