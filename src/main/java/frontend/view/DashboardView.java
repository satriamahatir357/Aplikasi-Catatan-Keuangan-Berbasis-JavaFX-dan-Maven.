package frontend.view;

import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

// Kita extends BorderPane supaya bisa dibaca sebagai Node oleh Scene
public class DashboardView extends BorderPane { 
    
    public DashboardView() {
        // Isian teks sementara buat pembuktian kalau aplikasimu jalan
        Text welcomeText = new Text("Selamat Datang di Aplikasi Dompetku!");
        welcomeText.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        // Taruh teks di tengah-tengah layar
        this.setCenter(welcomeText); 
    }
}