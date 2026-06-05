package frontend.view;

import javafx.geometry.Pos; // Pos adalah sebuah kelas dalam JavaFX yang digunakan untuk menentukan posisi elemen dalam layout.
import javafx.scene.control.Button; // Button adalah sebuah kelas dalam JavaFX yang digunakan untuk membuat tombol interaktif pada antarmuka pengguna. Tombol dapat digunakan untuk memicu aksi tertentu ketika pengguna mengkliknya, seperti membuka tampilan baru, menyimpan data, atau melakukan navigasi dalam aplikasi.
import javafx.scene.control.Label; // Label adalah sebuah kelas dalam JavaFX yang digunakan untuk menampilkan teks statis pada antarmuka pengguna. Label biasanya digunakan untuk memberikan informasi atau deskripsi kepada pengguna.
import javafx.scene.layout.BorderPane; // BorderPane adalah sebuah kelas dalam JavaFX yang digunakan untuk mengatur tata letak elemen-elemen UI dalam aplikasi. BorderPane memungkinkan Anda untuk menempatkan elemen-elemen di dalamnya
import javafx.scene.layout.StackPane; // StackPane adalah sebuah kelas dalam JavaFX yang digunakan untuk menumpuk elemen-elemen UI di atas satu sama lain. StackPane memungkinkan Anda untuk menempatkan elemen-elemen di dalamnya secara berurutan, sehingga elemen yang terakhir ditambahkan akan berada di atas elemen sebelumnya.
import javafx.scene.layout.VBox; // VBox adalah sebuah kelas dalam JavaFX yang digunakan untuk mengatur tata letak elemen-elemen secara vertikal. VBox memungkinkan Anda untuk menempatkan elemen-elemen di dalamnya secara berurutan dari atas ke bawah.

import frontend.view.DashboardView; // Mengimpor kelas DashboardView dari package frontend.view, yang merupakan tampilan untuk menampilkan informasi seperti total pemasukan, total pengeluaran, dan saldo kepada pengguna.
import frontend.view.TransaksiView; // Mengimpor kelas TransaksiView dari package frontend.view, yang merupakan tampilan untuk menambahkan transaksi baru, termasuk input keterangan, nominal, tipe transaksi, dan tanggal transaksi, serta menampilkan daftar transaksi yang telah ditambahkan.

public class MainLayout extends BorderPane { //BorderPane adalah sebuah kelas dalam JavaFX yang digunakan untuk mengatur tata letak elemen-elemen UI dalam aplikasi. BorderPane memungkinkan Anda untuk menempatkan elemen-elemen di dalamnya dengan cara yang terstruktur, seperti menempatkan elemen di bagian atas, bawah, kiri, kanan, dan tengah aplikasi. Dengan menggunakan BorderPane, Anda dapat dengan mudah mengatur tampilan aplikasi Anda sesuai dengan kebutuhan desain yang diinginkan.
    public MainLayout() {

        // === Header ===
        Label title = new Label("DOMPETKU"); // Membuat sebuah objek Label yang akan digunakan sebagai judul dalam aplikasi. Label adalah kelas dalam JavaFX yang digunakan untuk menampilkan teks statis pada antarmuka pengguna. Dengan membuat objek

        VBox header = new VBox(title); // Membuat sebuah objek VBox yang akan digunakan sebagai header dalam aplikasi. VBox adalah kelas dalam JavaFX yang digunakan untuk mengatur tata letak elemen-elemen secara vertikal. Dengan membuat objek VBox, kita dapat menambahkan elemen-elemen seperti judul "DOMPETKU" ke dalamnya, sehingga akan ditampilkan secara vertikal di bagian atas aplikasi.
        header.setAlignment(Pos.CENTER); // Mengatur posisi elemen di dalam header agar berada di tengah secara horizontal dan vertikal menggunakan metode setAlignment() dengan parameter Pos.CENTER.

        // === SIDEBAR ===

        Button dashboardButton = new Button("Dashboard");
        Button transaksiButton = new Button("Transaksi");

        VBox sidebar = new VBox( // Membuat sebuah objek VBox yang akan digunakan sebagai sidebar dalam aplikasi. VBox adalah kelas dalam JavaFX yang digunakan untuk mengatur tata letak elemen-elemen secara vertikal. Dengan membuat objek VBox, kita dapat menambahkan tombol-tombol navigasi seperti dashboardButton dan transaksiButton ke dalamnya, sehingga akan ditampilkan secara vertikal di sisi kiri aplikasi.
                dashboardButton,
                transaksiButton
        );

        dashboardButton.setPrefWidth(120); // Mengatur lebar tombol dashboardButton dan transaksiButton menjadi 120 piksel menggunakan metode setPrefWidth(120) dari objek tombol, sehingga tombol-tombol tersebut memiliki ukuran yang konsisten dan mudah untuk diklik oleh pengguna.
        transaksiButton.setPrefWidth(120); // Mengatur lebar tombol dashboardButton dan transaksiButton menjadi 120 piksel menggunakan metode setPrefWidth(120) dari objek tombol, sehingga tombol-tombol tersebut memiliki ukuran yang konsisten dan mudah untuk diklik oleh pengguna.

        sidebar.setSpacing(20); // Mengatur jarak vertikal antara tombol-tombol di dalam sidebar sebesar 20 piksel menggunakan metode setSpacing(20) dari objek sidebar, sehingga tombol-tombol tersebut tidak terlalu berdekatan dan tampilan menjadi lebih rapi.
        sidebar.setStyle("-fx-padding: 20;"); // Mengatur padding (jarak antara tepi sidebar dengan elemen di dalamnya) sebesar 20 piksel menggunakan metode setStyle() dengan parameter "-fx-padding: 20;". Ini akan memberikan ruang yang cukup antara tepi sidebar dan tombol-tombol navigasi di dalamnya, sehingga tampilan menjadi lebih rapi dan nyaman untuk dilihat.
        header.setPrefHeight(60); // Mengatur tinggi header menjadi 60 piksel menggunakan metode setPrefHeight() dari objek header, sehingga akan memberikan ruang

        // === CONTENT AREA ===
        StackPane contentArea = new StackPane(); // StackPane adalah sebuah kelas dalam JavaFX yang digunakan untuk menumpuk elemen-elemen UI di atas satu sama lain. StackPane memungkinkan Anda untuk menempatkan elemen-elemen di dalamnya secara berurutan, sehingga elemen yang terakhir ditambahkan akan berada di atas elemen sebelumnya.
        DashboardView dashboardView = new DashboardView(); // Membuat sebuah objek DashboardView yang akan digunakan sebagai tampilan utama dalam area konten. DashboardView adalah kelas yang berisi tampilan untuk menampilkan informasi seperti total pemasukan, total pengeluaran, dan saldo kepada pengguna.
        TransaksiView transaksiView = new TransaksiView(); // Membuat sebuah objek TransaksiView yang akan digunakan sebagai tampilan untuk menambahkan transaksi baru. TransaksiView adalah kelas yang berisi tampilan untuk menambahkan transaksi baru, termasuk input keterangan, nominal, tipe transaksi, dan tanggal transaksi, serta menampilkan daftar transaksi yang telah ditambahkan.
        
        dashboardButton.setOnAction(e ->{
            contentArea.getChildren().clear();
            contentArea.getChildren().add(dashboardView);
        });

        transaksiButton.setOnAction(e ->{
            contentArea.getChildren().clear(); // Menghapus semua elemen yang ada di dalam contentArea menggunakan metode getChildren().clear(), sehingga ketika tombol transaksiButton diklik, tampilan sebelumnya (seperti DashboardView) akan dihapus dari area konten sebelum menampilkan TransaksiView.
            contentArea.getChildren().add(transaksiView); // Menambahkan transaksiView ke dalam contentArea, sehingga ketika tombol transaksiButton diklik, tampilan TransaksiView akan ditampilkan di area konten.
        });

        // === PASANG KE BORDERPANE ===
        setTop(header); // Menempatkan header di bagian atas BorderPane, sehingga akan menampilkan judul "DOMPETKU" di bagian atas aplikasi.
        setLeft(sidebar);  // Menempatkan sidebar di bagian kiri BorderPane, sehingga akan menampilkan tombol-tombol navigasi seperti
        setCenter(contentArea); // Menempatkan contentArea di bagian tengah BorderPane, sehingga akan menampilkan DashboardView sebagai tampilan utama saat aplikasi dijalankan.

    }
    
}
