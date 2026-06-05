package frontend;

import frontend.view.DashboardView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage){
        DashboardView dashboardView = new DashboardView(); // Membuat sebuah objek DashboardView yang akan digunakan sebagai tampilan utama dalam aplikasi JavaFX. Dengan membuat objek
        Scene scene = new Scene(dashboardView, 800, 600); // Membuat sebuah objek Scene yang akan menampilkan DashboardView dengan ukuran 800 piksel lebar dan 600 piksel tinggi. Scene adalah wadah utama untuk menampilkan elemen-elemen UI dalam aplikasi JavaFX.
        stage.setTitle("Dompetku"); // Mengatur judul jendela aplikasi JavaFX menjadi "Dompetku" menggunakan metode setTitle() dari objek stage.
        stage.setScene(scene); // Menetapkan scene yang telah dibuat sebelumnya ke dalam stage menggunakan metode setScene() dari objek stage, sehingga DashboardView akan ditampilkan saat aplikasi dijalankan.
        stage.show(); // Menampilkan jendela aplikasi JavaFX dengan memanggil metode show() dari objek stage, sehingga pengguna dapat melihat tampilan DashboardView yang telah dibuat.
    }

    public static void main(String [] args){
        launch(args); // Memanggil metode launch() dari kelas Application untuk memulai aplikasi JavaFX. Metode ini akan memanggil metode start() yang telah di override sebelumnya, sehingga aplikasi akan berjalan dan menampilkan tampilan DashboardView.
    }
}
