package frontend.view;

import backend.DashboardService;
import backend.FilterService;
import backend.TransaksiService;
import javafx.geometry.Pos; // Pos adalah sebuah kelas dalam JavaFX yang digunakan untuk menentukan posisi elemen dalam layout.
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label; // Label adalah sebuah kelas dalam JavaFX yang digunakan untuk menampilkan teks statis pada antarmuka pengguna. Label biasanya digunakan untuk memberikan informasi atau deskripsi kepada pengguna.
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox; // HBox adalah sebuah kelas dalam JavaFX yang digunakan untuk mengatur tata letak elemen-elemen secara horizontal. HBox memungkinkan Anda untuk menempatkan elemen-elemen di dalamnya secara berurutan dari kiri ke kanan.
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox; // VBox adalah sebuah kelas dalam JavaFX yang digunakan untuk mengatur tata letak elemen-elemen secara vertikal. VBox memungkinkan Anda untuk menempatkan elemen-elemen di dalamnya secara berurutan dari atas ke bawah.

import java.text.NumberFormat;
import java.util.Locale;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import java.util.Set;

import javafx.scene.text.Text;
import model.Transaksi;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.layout.Region;
import javafx.scene.layout.Priority;

public class DashboardView extends VBox { // DashboardView adalah sebuah kelas yang merupakan turunan dari VBox, yang digunakan untuk membuat tampilan dashboard dalam aplikasi. DashboardView akan menampilkan informasi seperti total pemasukan, total pengeluaran, dan saldo kepada pengguna.

        // field class
        // ===== SERVICE =====
        private final DashboardService dashboardService;
        private final FilterService filterService;
        private final TransaksiService transaksiService;

        // ===== CARD =====
        private Label pemasukanValue;
        private Label pengeluaranValue;
        private Label saldoValue;
        private Label jumlahTransaksiValue;
        private Label rataRataValue;
        private VBox dashboardCards;

        // ===== CHART =====
        private BarChart<String, Number> chart;
        private StackPane labelPane;
        private StackPane chartContainer;

        // ===== FILTER =====
        private ComboBox<String> filterBox; // ComboBox untuk memilih filter data pada dashboard, misalnya filter berdasarkan bulan atau tahun.
        
    public DashboardView(TransaksiService transaksiService) {
        this.transaksiService = transaksiService;
        this.dashboardService = new DashboardService(transaksiService); // DashboardService memakai TransaksiService sebagai sumber data.
        this.filterService =  new FilterService(); // FilterService digunakan untuk memfilter data transaksi berdasarkan tipe atau periode tertentu.

        // ===== CARD =====
        createDashboardCards();

        // ===== CHART =====
        createChart();

        // ===== FILTER =====
        createFilter();

        // ===== LAYOUT =====
        createLayout();

        // memanggil method refreshDashboard
        refreshDashboard();
    }

    // ===== CARD =====
    private void createDashboardCards() {
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
        dashboardCards = new VBox(
            row1,
            row2
        );
        
        dashboardCards.getStyleClass().add("card-container");
    }

    // ===== CHART =====
    private void createChart() {
        // === Buat chart ===
        // Buat sumbu X untuk kategori, buat sumbu Y untuk angka, lalu gunakan kedua sumbu tersebut untuk membuat sebuah diagram batang (BarChart)
        CategoryAxis xAxis = new CategoryAxis(); // Membuat sumbu X (horizontal) yang berisi kategori atau teks.
        NumberAxis yAxis = new NumberAxis(); // Membuat sumbu Y (vertikal) yang berisi angka.

        chart = new BarChart<>(xAxis, yAxis); // Buat sebuah diagram batang menggunakan: xAxis sebagai sumbu horizontal, yAxis sebagai sumbu vertikal
        chart.setCategoryGap(40);
        chart.setBarGap(5);
        
        // Menghilangkan garis pada barChart
        chart.setHorizontalGridLinesVisible(false); // Sembunyikan garis horizontal pada grafik.
        chart.setVerticalGridLinesVisible(false); // Sembunyikan garis vertikal pada grafik.

        chart.setTitle("Grafik Keuangan");
        chart.getStyleClass().add("chart-title");
        xAxis.setLabel("Kategori");
        yAxis.setLabel("Nominal (Rp)");

        chart.getStyleClass().add("chart");
        
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        chart.setPrefHeight(330);

        // === Buat labelPane ===
        labelPane = new StackPane(); // labelPane digunakan untuk menaruh objek Text di atas chart
        labelPane.setMouseTransparent(true); // Agar labelPane tidak mengganggu interaksi pengguna dengan chart, labelPane diatur agar tidak menerima input mouse.

        chartContainer = new StackPane(
            chart,
            labelPane
        );
    }

    // ===== FILTER =====
    private void createFilter() {
        // == Filter berdasarkan periode ==
        filterBox = new ComboBox<>();
        
        filterBox.getItems().addAll(
            "Semua",
            "Hari ini",
            "7 Hari",
            "30 Hari",
            "Bulan ini",
            "Tahun ini"
        );

        filterBox.setValue("Semua"); // Set default value dari ComboBox menjadi "Semua"
        filterBox.setPrefWidth(140); // Set lebar ComboBox menjadi 140 piksel
        filterBox.setPrefHeight(40);

        filterBox.setOnAction(e -> {
            String periode = filterBox.getValue(); // Ambil nilai yang dipilih dari ComboBox dan simpan dalam variabel periode.
            ObservableList<Transaksi> daftarTransaksi = 
                filterService.filterPeriode(
                    transaksiService.getDaftarTransaksi(),
                    periode // Memanggil method filterPeriode() dari TransaksiService untuk memfilter daftar transaksi berdasarkan periode yang dipilih. Hasil filter disimpan dalam variabel daftarTransaksi.
                );

            refreshDashboard(daftarTransaksi); // Memanggil method refreshDashboard dengan daftarTransaksi yang telah difilter berdasarkan periode yang dipilih.
        });
    }

    // ===== LAYOUT =====
    private void createLayout() {
        // Styling Layout
        getStyleClass().add("dashboard-view");

        Label title = new Label("Dashboard");
        title.getStyleClass().add("dashboard-title");

        Region spacer = new Region(); // Membuat sebuah Region kosong yang akan digunakan sebagai spacer untuk memisahkan elemen-elemen dalam HBox.
        HBox.setHgrow(spacer, Priority.ALWAYS); // Mengatur agar spacer dapat mengisi ruang kosong yang tersedia dalam HBox, sehingga elemen-elemen lain akan terdorong ke sisi kiri dan kanan.

        HBox header = new HBox(
            title,
            spacer,
            filterBox
        );
 
        header.setAlignment(Pos.CENTER_LEFT); // Mengatur agar semua elemen dalam HBox header sejajar ke kiri secara vertikal.

        VBox content = new VBox(
            header,
            dashboardCards,
            chartContainer
        );

        content.setSpacing(15);
        content.setPadding(new Insets(20)); // Mengatur jarak antara elemen-elemen dalam VBox content sebesar 20 piksel di semua sisi (atas, kanan, bawah, kiri).
        
        // ScrollPane
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true); // Mengatur agar konten dalam ScrollPane menyesuaikan lebar ScrollPane, sehingga tidak ada ruang kosong di sisi kiri atau kanan konten.
        scrollPane.setPannable(true); // Mengatur agar konten dalam ScrollPane dapat digeser secara horizontal dan vertikal jika konten melebihi ukuran ScrollPane.

        // Masukkan card ke DashboardView
        getChildren().add(scrollPane);
        scrollPane.getStyleClass().add("dashboard-scroll");
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

    private String formatRupiah(double nominal){

        NumberFormat rupiah =
            NumberFormat.getCurrencyInstance(
                new Locale("id", "ID")
            );

            return rupiah.format(nominal);
    }

    // Method refreshDashboard
    public void refreshDashboard(){
        refreshDashboard(transaksiService.getDaftarTransaksi());
    }

    // Method refreshDashboard dengan parameter ObservableList<Transaksi> daftar
    // perbedaan dengan method refreshDashboard() sebelumnya adalah method ini menerima parameter ObservableList<Transaksi> daftar, sehingga dapat memperbarui tampilan dashboard berdasarkan daftar transaksi yang diberikan sebagai argumen.
    public void refreshDashboard(ObservableList<Transaksi> daftar) {
        double pemasukan = dashboardService.getTotalPemasukan(daftar);
        double pengeluaran = dashboardService.getTotalPengeluaran(daftar);
        double rataRata = dashboardService.getRataRataNominal(daftar);

        updateDashboard(pemasukan, pengeluaran, rataRata);

        jumlahTransaksiValue.setText(
            dashboardService.getJumlahTransaksi(daftar) + " Transaksi"
        );

        updateChart(daftar);
    }

    public void updateChart() {
        updateChart(transaksiService.getDaftarTransaksi());
    }

    public void updateChart(ObservableList<Transaksi> daftar) {
        chart.getData().clear(); // Menghapus semua data yang ada di chart agar tidak menumpuk saat chart diperbarui.
        labelPane.getChildren().clear(); // Menghapus semua label yang ada di labelPane agar tidak menumpuk saat chart diperbarui.
        
        if(daftar.isEmpty()){
            chart.setVisible(false); // Jika daftar transaksi kosong, maka chart akan disembunyikan agar tidak menampilkan grafik kosong.

            Label emptyLabel = new Label("Belum ada data transaksi");
            emptyLabel.getStyleClass().add("empty-chart-label");

            labelPane.getChildren().add(emptyLabel);

            labelPane.setAlignment(Pos.CENTER);
            return;

        }
        chart.setVisible(true); // Jika daftar transaksi tidak kosong, maka chart akan ditampilkan agar grafik dapat terlihat.
        
        // Series Pemasukan
        XYChart.Series<String, Number> pemasukanSeries = new XYChart.Series<>(); // Membuat sebuah series baru untuk data pemasukan. Series ini akan berisi pasangan kategori (String) dan nilai (Number) yang akan ditampilkan pada chart.
        pemasukanSeries.getData().add( // Menambahkan data pemasukan ke dalam series. Data ini terdiri dari kategori "Pemasukan" dan nilai total pemasukan yang diperoleh dari dashboardService.
            new XYChart.Data<>( // Membuat sebuah objek Data baru yang berisi kategori dan nilai. Objek ini akan ditambahkan ke dalam series pemasukanSeries.
                "Pemasukan",
                dashboardService.getTotalPemasukan(daftar) 
            )
        );

        // Series Pengeluaran
        XYChart.Series<String, Number> pengeluaranSeries = new XYChart.Series<>();
        pengeluaranSeries.getData().add(
            new XYChart.Data<>(
                "Pengeluaran",
                dashboardService.getTotalPengeluaran(daftar)
            )
        );

        // Series Saldo
        XYChart.Series<String, Number> saldoSeries = new XYChart.Series<>();
        saldoSeries.getData().add(
            new XYChart.Data<>(
                "Saldo",
                dashboardService.getSaldo(daftar)
            )
        );

        // Tooltip pada setiap batang
        Tooltip pemasukanTooltip = new Tooltip( // Membuat tooltip untuk batang pemasukan
            "Pemasukan\n" + formatRupiah(dashboardService.getTotalPemasukan(daftar)) // Menampilkan total pemasukan dalam format Rupiah pada tooltip.
        );
        
        Tooltip pengeluaranTooltip = new Tooltip(
            "Pengeluaran\n" + formatRupiah(dashboardService.getTotalPengeluaran(daftar))
        );
        
        Tooltip saldoTooltip = new Tooltip(
            "Saldo\n" + formatRupiah(dashboardService.getSaldo(daftar))
        );
        
        chart.getData().addAll( // Menambahkan semua series (pemasukan, pengeluaran, saldo) ke dalam chart agar ditampilkan pada diagram batang.
            pemasukanSeries,
            pengeluaranSeries,
            saldoSeries
        );

        // Pasang efek pada setiap batang chart
        Platform.runLater(() -> {  // Menjalankan kode ini pada thread JavaFX setelah semua node chart telah dirender, sehingga node batang sudah tersedia untuk dipasang efek.
            pasangEfek( // Memanggil method pasangEfek untuk memasang efek pada batang pemasukan.
                pemasukanSeries.getData().get(0),
                pemasukanTooltip
            );

            pasangEfek(
                pengeluaranSeries.getData().get(0),
                pengeluaranTooltip
            );

            pasangEfek(
                saldoSeries.getData().get(0),
                saldoTooltip
            );
        });

    }

    // Method untuk memasang efek pada batang chart
    private void pasangEfek(
        XYChart.Data<String, Number> data, // Parameter data adalah objek Data yang berisi kategori dan nilai dari batang chart yang akan dipasang efek.
        Tooltip tooltip) { 

        Node bar = data.getNode(); // Mendapatkan node batang dari data chart. Node ini adalah representasi visual dari batang chart yang akan dipasang efek.

        if (bar == null) { // Jika node batang belum tersedia (misalnya karena chart belum dirender sepenuhnya), maka method ini akan mengembalikan nilai dan tidak melakukan apa-apa.
            return;
        }

        Tooltip.install(bar, tooltip); // Memasang tooltip pada node batang. Tooltip akan muncul ketika pengguna mengarahkan kursor mouse ke batang chart.

        // Efek hover pada batang chart
        bar.setOnMouseEntered(e -> {
            bar.setScaleX(1.1); // Mengubah skala X (lebar) batang menjadi 1.1 kali ukuran aslinya saat mouse masuk ke area batang, sehingga batang terlihat lebih besar.
            bar.setScaleY(1.05); // Mengubah skala Y (tinggi) batang menjadi 1.05 kali ukuran aslinya saat mouse masuk ke area batang, sehingga batang terlihat lebih tinggi.
        });

        bar.setOnMouseExited(e -> {
            bar.setScaleX(1);
            bar.setScaleY(1);
        });
    }

}