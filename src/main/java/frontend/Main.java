package frontend;

import frontend.view.DashboardView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        // Membuat objek dari DashboardView kamu
        DashboardView dashboardView = new DashboardView(); 
        
        // Membuat Scene dengan ukuran window 800x600
        // Catatan: Pastikan DashboardView kamu meng-extends Parent (seperti BorderPane, AnchorPane, atau VBox)
        Scene scene = new Scene(dashboardView, 800, 600); 
        
        stage.setTitle("Dompetku - Aplikasi Catatan Keuangan"); 
        stage.setScene(scene); 
        stage.show(); 
    }

    public static void main(String[] args) {
        launch(args);
    }
}