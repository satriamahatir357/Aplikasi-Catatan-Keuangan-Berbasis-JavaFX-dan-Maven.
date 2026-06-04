package frontend.view;

import javafx.scene.layout.VBox; // VBox adalah sebuah kelas dalam JavaFX yang digunakan untuk mengatur tata letak (layout) vertikal dari elemen-elemen dalam sebuah antarmuka pengguna (UI). Dengan menggunakan
import javafx.geometry.Pos; // Pos adalah sebuah kelas dalam JavaFX yang digunakan untuk menentukan posisi elemen-elemen dalam sebuah tata letak (layout). Dengan menggunakan Pos, Anda dapat mengatur posisi elemen-elemen seperti label, tombol, atau gambar dalam sebuah antarmuka pengguna (UI) sesuai dengan kebutuhan desain Anda.
import javafx.scene.control.Label; // Label adalah sebuah kelas dalam JavaFX yang digunakan untuk menampilkan teks statis dalam sebuah antarmuka pengguna (UI). Dengan menggunakan Label, Anda dapat menampilkan informasi, judul, atau deskripsi dalam aplikasi JavaFX Anda.
import javafx.scene.layout.HBox; // HBox adalah sebuah kelas dalam JavaFX yang digunakan untuk mengatur tata letak (layout) horizontal dari elemen-elemen dalam sebuah antarmuka pengguna (UI). Dengan menggunakan HBox, elemen-elemen dalam UI akan diatur secara horizontal, sehingga Anda dapat menempatkan label, tombol, atau gambar secara berdampingan dalam aplikasi JavaFX Anda.

public class DashboardView extends Vbox{ // DashboardView adalah sebuah kelas yang merupakan turunan dari VBox, yang digunakan untuk membuat tampilan dashboard dalam aplikasi JavaFX. Dengan menggunakan VBox, elemen-elemen dalam dashboard akan diatur secara vertikal.
    public DasboardView(){ // Konstruktor untuk kelas DashboardView, yang akan dipanggil saat objek DashboardView dibuat. Di dalam konstruktor ini, Anda dapat menambahkan elemen-elemen UI seperti label, tabel, atau grafik yang akan ditampilkan di dashboard.
        Label pemasukkanTitle = new Label("Total Pemasukkan"); // Membuat sebuah label dengan teks "Total Pemasukkan" yang akan digunakan sebagai judul untuk bagian pemasukan dalam dashboard.
        Label pengeluaranVelue = new Label("Rp 0"); // Membuat sebuah label dengan teks "Rp 0" yang akan digunakan untuk menampilkan nilai total pengeluaran dalam dashboard. Nilai ini dapat diperbarui secara dinamis sesuai dengan data transaksi yang ada.

        VBox pemasukanCard = new Vbox( // Membuat sebuah VBox yang akan digunakan sebagai wadah untuk elemen-elemen terkait pemasukan dalam dashboard. Dengan menggunakan VBox, elemen-elemen seperti judul dan nilai pemasukan akan diatur secara vertikal.
            pemasukkanTitle,// Menambahkan label pemasukkanTitle ke dalam VBox pemasukanCard, sehingga akan ditampilkan sebagai judul untuk bagian pemasukan dalam dashboard.
            pemasukkanValue // Menambahkan label pemasukkanTitle dan pemasukkanValue ke dalam VBox pemasukanCard, sehingga keduanya akan ditampilkan secara vertikal dalam dashboard.
        );

        Label pengeluaranTitle = new Label("Total Pengeluaran"); // Membuat sebuah label dengan teks "Total Pengeluaran" yang akan digunakan sebagai judul untuk bagian pengeluaran dalam dashboard.
        Label pengeluaranValue = new Label("Rp 0");

        VBox pengeluaranCard = new VBox(
            pengeluaranTitle,
            pengeluaranValue
        );

        Label saldoTitle = new Label("Saldo");
        Label saldoValue = new Label(Rp 0);

        VBox saldoCard = new VBox(
            saldoTitle,
            saldoValue
        );

        HBox cardContainer = new HBox( // Membuat sebuah HBox yang akan digunakan sebagai wadah untuk ketiga VBox (pemasukanCard, pengeluaranCard, saldoCard) dalam dashboard. Dengan menggunakan HBox, ketiga VBox akan diatur secara horizontal dalam dashboard.
            pemasukanCard,
            pengeluaranCard,
            saldoCard
        );

        getChildren().add(cardContainer); // Menambahkan HBox cardContainer ke dalam DashboardView, sehingga ketiga VBox (pemasukanCard, pengeluaranCard, saldoCard) akan ditampilkan secara horizontal dalam dashboard.
        
        setSpacing(20); // Mengatur jarak antar elemen dalam HBox cardContainer menjadi 20 piksel, sehingga ada ruang yang cukup antara ketiga VBox (pemasukanCard, pengeluaranCard, saldoCard) dalam dashboard.
        cardContainer.setSpacing(20); // Mengatur jarak antar elemen dalam HBox cardContainer menjadi 20 piksel, sehingga ada ruang yang cukup antara ketiga VBox (pemasukanCard, pengeluaranCard, saldoCard) dalam dashboard.
        setAligment(Pos.CENTER); // Mengatur posisi elemen-elemen dalam HBox cardContainer menjadi rata tengah (center), sehingga ketiga VBox (pemasukanCard, pengeluaranCard, saldoCard) akan ditampilkan di tengah-tengah dashboard.
        cardContainer.setAlignment(Pos.CENTER); // Mengatur posisi elemen-elemen dalam HBox cardContainer menjadi rata tengah (center), sehingga ketiga VBox (pemasukanCard, pengeluaranCard, saldoCard) akan ditampilkan di tengah-tengah dashboard.

    }
}
