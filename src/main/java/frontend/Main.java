package frontend;

import frontend.view.MainLayout;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage){
        MainLayout mainLayout = new MainLayout(); // Membuat sebuah objek MainLayout yang akan digunakan sebagai layout utama dalam aplikasi. MainLayout adalah kelas yang berisi struktur dasar tampilan aplikasi, termasuk header, sidebar, dan area konten.
        Scene scene = new Scene(mainLayout, 1000, 700); // Membuat sebuah objek Scene yang akan menampilkan DashboardView dengan ukuran 800 piksel lebar dan 600 piksel tinggi. Scene adalah wadah utama untuk menampilkan elemen-elemen UI dalam aplikasi JavaFX.
        scene.getStylesheets().add(getClass()
                .getResource("/css/global.css") // getResource("/css/global.css") digunakan untuk mendapatkan URL dari file CSS yang berada di dalam folder resources/css dengan nama global.css. Ini memungkinkan aplikasi untuk memuat file CSS yang berisi aturan gaya untuk tampilan aplikasi.
                .toExternalForm() // toExternalForm() digunakan untuk mengonversi URL yang diperoleh dari getResource() menjadi format yang dapat digunakan oleh JavaFX untuk memuat file CSS.
            );   
        
        stage.setTitle("Dompetku"); // Mengatur judul jendela aplikasi JavaFX menjadi "Dompetku" menggunakan metode setTitle() dari objek stage.
        stage.setScene(scene); // Menetapkan scene yang telah dibuat sebelumnya ke dalam stage menggunakan metode setScene() dari objek stage, sehingga DashboardView akan ditampilkan saat aplikasi dijalankan.
        stage.show(); // Menampilkan jendela aplikasi JavaFX dengan memanggil metode show() dari objek stage, sehingga pengguna dapat melihat tampilan DashboardView yang telah dibuat.
    }

    public static void main(String [] args){
        launch(args); // Memanggil metode launch() dari kelas Application untuk memulai aplikasi JavaFX. Metode ini akan memanggil metode start() yang telah di override sebelumnya, sehingga aplikasi akan berjalan dan menampilkan tampilan DashboardView.
    }
}
