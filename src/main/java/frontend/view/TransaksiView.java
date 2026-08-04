package frontend.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert; // komponen popup dialog bawaan JavaFX yang dipakai untuk menampilkan pesan ke user.
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.time.LocalDate;
import java.util.Optional;

import backend.FilterService;
import backend.SearchService;
import backend.SortService;
import backend.TransaksiService; // Mengimpor kelas TransaksiService dari package backend, yang merupakan layanan untuk mengelola transaksi yang akan digunakan dalam view ini
import model.Transaksi; // Mengimpor kelas Transaksi dari package model, yang merupakan model data untuk transaksi yang akan digunakan dalam
import javafx.scene.control.TableCell;

public class TransaksiView extends VBox { // extends VBox untuk membuat layout vertikal
    
    
    // === SERVICE ===
    private TransaksiService transaksiService; // Deklarasi variabel transaksiService untuk mengelola transaksi dalam view ini
    private SearchService searchService;
    private FilterService filterService;
    private SortService sortService;
    
    // === VIEW ===
    private DashboardView dashboardView;
    
    // === SEARCH ===
    private TextField searchField;
    private String keyword = "";
    
    // === FILTER ===
    private ComboBox<String> filterBox;
    
    // === SORT ===
    private ComboBox<String> sortBox;

    // === Form ===
    private TextField keteranganField;
    private TextField nominalField;
    private ComboBox<String> tipeBox;
    private DatePicker tanggalPicker;
    
    // === button ===
    private Button tambahButton;
    private Button batalButton;
    
    // === table ===
    private TableView<Transaksi> transaksiTable;
    private TableColumn<Transaksi,String> keteranganColumn;
    private TableColumn<Transaksi,Double> nominalColumn;
    private TableColumn<Transaksi,String> tipeColumn;
    private TableColumn<Transaksi,String> tanggalColumn;
    private TableColumn<Transaksi,Void> editColumn;
    private TableColumn<Transaksi,Void> hapusColumn;
    
    // === edit ===
    private Transaksi transaksiYangDiedit = null;

    public TransaksiView(TransaksiService transaksiService, DashboardView dashboardView){
        this.transaksiService = transaksiService;
        this.dashboardView = dashboardView;
        this.searchService = new SearchService();
        this.filterService = new FilterService();
        this.sortService = new SortService();
        
        // === filterbox ===
        createFilter(); // memanggil method filterbox
        setupFilterEvent(); // memanggil method event filterbox

        // === softBox ===
        createSort();
        setupSortEvent();

        // === search ===
        createSearch();
        setupSearchEvent();

        // === FORM ===
        createForm();
        
        // === button ===
        createButton();

        // === tabel ===
        createTable();

        // === Menambahkan logika tombol Edit dan Hapus yang di dalam tabel ===
        setupEditCellFactory();
        setupHapusCellFactory();

        // label
        Label title = new Label("Tambah Transaksi");
        title.getStyleClass().add("from-title");
        
        // Empty State Table
        Label emptyLabel = new Label("Belum ada transaksi");
        emptyLabel.getStyleClass().add("empty-table-label");

        transaksiTable.setPlaceholder(emptyLabel);
        
        transaksiTable.getColumns().addAll( // menambahkan kolom-kolom ke dalam tabel transaksi
            keteranganColumn,
            nominalColumn,
            tipeColumn,
            tanggalColumn,
            editColumn,
            hapusColumn
        ); // Menambahkan kolom-kolom ke dalam tabel transaksi

        // Isi lebar tabel secara otomatis
        transaksiTable.setColumnResizePolicy(
            TableView.CONSTRAINED_RESIZE_POLICY
        );

        // layout
        setSpacing(10); // setSpacing untuk memberikan jarak antar elemen dalam VBox

        Label tableTitle = new Label("Daftar Transaksi");
        tableTitle.getStyleClass().add("form-title");

        HBox buttonBox = new HBox(
            tambahButton,
            batalButton
        );

        buttonBox.setSpacing(15);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        VBox formBox = new VBox(
            keteranganField,
            nominalField,
            tipeBox,
            tanggalPicker,
            buttonBox
        );
        formBox.setSpacing(15);

        VBox formCard = new VBox(
            tableTitle,
            formBox
        );
        formCard.getStyleClass().add("form-card");

        // Buat container Search + Filter
        HBox searchFilterBox = new HBox(
            searchField,
            filterBox,
            sortBox
        );
        searchFilterBox.setSpacing(10);
        searchFilterBox.setAlignment(Pos.CENTER_LEFT);

        VBox tableCard = new VBox(
            tableTitle,
            searchFilterBox,
            transaksiTable
        );

        tableCard.getStyleClass().add("table-card");

        VBox content = new VBox(
            formCard,
            tableCard
        );

        getChildren().add(content);
        updateTable();

        // scroll ui
        content.getStyleClass().add("transaksi-view");

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true); //setFitToWidth(true) = Lebar isi ScrollPane akan mengikuti lebar ScrollPane.
        scrollPane.setPannable(true); // setPannable(true) = Isi ScrollPane bisa digeser (drag) menggunakan mouse atau touchpad.

        getChildren().add(scrollPane);
        scrollPane.getStyleClass().add("transaksi-scroll");


        // event handler untuk tombol tambah
        tambahButton.setOnAction(e -> {
            // Ambil data dari inputan

            // input keterangan
            String keterangan = keteranganField.getText(); // Mengambil teks dari keteranganField dan menyimpannya dalam variabel keterangan
            if (keterangan.isBlank()) {
                showWarning("Keterangan tidak boleh kosong!");
                return;
            }

            // == input nominal ==
            // validasi ketika kosong
            if (nominalField.getText().isBlank()) {
                showWarning("Nominal tidak boleh kosong!");
                return;
            }
            
            // validasi ketika input bukan angka
            double nominal;
            
            try {
                nominal = Double.parseDouble(nominalField.getText());
            }catch (NumberFormatException ex) {
                showWarning("Nominal harus berupa angka!");
                return;
            }

            // memilih pemasukan atau pengeluaran
            String tipe = tipeBox.getValue(); // Mengambil nilai yang dipilih dari tipeBox dan menyimpannya dalam variabel tipe
            
            // memilih tanggal transaksi
            if (tanggalPicker.getValue() == null) {
                showWarning("Tanggal harus dipilih!");
                return;
            }

            String tanggal = tanggalPicker.
                            getValue().
                            toString(); // Mengambil nilai yang dipilih dari tanggalPicker, mengubahnya menjadi string, dan menyimpannya dalam variabel tanggal

            Transaksi transaksi = new Transaksi(keterangan, nominal, tipe, tanggal);

            if(transaksiYangDiedit == null){
                // Mode tamah
                transaksiService.tambahTransaksi(transaksi); // Memanggil metode tambahTransaksi pada transaksiService untuk menambahkan transaksi baru ke dalam daftar transaksi yang dikelola oleh transaksiService
            }
            else{
                transaksiYangDiedit.setKeterangan(keterangan);
                transaksiYangDiedit.setNominal(nominal);
                transaksiYangDiedit.setTipe(tipe);
                transaksiYangDiedit.setTanggal(tanggal);

                transaksiTable.refresh();

                transaksiService.updateTransaksi();

                transaksiYangDiedit = null;
                tambahButton.setText("Tambah");
                batalButton.setVisible(false);
                batalButton.setManaged(false);
            }
            updateTable(); 
            dashboardView.refreshDashboard(); 

            // scroll ui
            keteranganField.clear();
            nominalField.clear();
            tanggalPicker.setValue(null);
            tipeBox.setValue("Pemasukan");
        
        });


        // === tombol batal edit ===
        batalButton.setOnAction(e -> {
            transaksiYangDiedit = null;

            keteranganField.clear();
            nominalField.clear();
            tanggalPicker.setValue(null);
            tipeBox.setValue("Pemasukan");

            tambahButton.setText("Tambah");
            batalButton.setVisible(false);
            batalButton.setManaged(false);
        });
    }

    // method untuk filter
    private void createFilter(){
        // inisialisasi filterBox
        filterBox = new ComboBox<>();
        filterBox.getItems().addAll("Semua", "Pemasukan", "Pengeluaran");
        filterBox.setValue("Semua");
        filterBox.getStyleClass().add("filter-box");
    }

    // method event filterbox
    private void setupFilterEvent(){
        // Setiap kali pengguna mengganti pilihan pada filterBox, panggil updateTable() agar isi tabel diperbarui sesuai filter yang dipilih.
        filterBox.valueProperty().addListener((observable, oldValue, newValue)-> {
        updateTable();
        });
    }

    // method sortBox
    private void createSort(){
        // Buat sortBox
        sortBox = new ComboBox<>();
        
        sortBox.getItems().addAll(
            "Terbaru",
            "Terlama",
            "Nominal Terbesar",
            "Nominal Terkecil",
            "A-Z",
            "Z-A"
        );
        sortBox.setValue("Terbaru");
        sortBox.getStyleClass().add("sort-box");
    }

    // method untuk event sortBox
    private void setupSortEvent(){
        // Setiap kali pilihan pada sortBox berubah, panggil updateTable() agar tabel diperbarui sesuai pilihan terbaru
        sortBox.valueProperty() // Ambil property nilai yang sedang dipilih pada sortBox.
        .addListener( // Tambahkan "pendengar" (listener).
        (observable, oldValue, newValue)-> { // Ini adalah lambda expression. Artinya: Saat nilai berubah, Java memberikan tiga informasi:
            updateTable(); // Muat ulang isi tabel sesuai pilihan terbaru.
        });
    }
    
    // method untuk search
    private void createSearch(){
        // search
        searchField = new TextField();
        searchField.setPromptText("Cari transaksi...");
        searchField.getStyleClass().add("search-field");
        searchField.setMaxWidth(Double.MAX_VALUE);
    }
    
    // method untuk event search
    private void setupSearchEvent(){
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            keyword = newValue;
            updateTable();
        });
    }

    // === Form ===
    private void createForm(){
        // input keterangan
        keteranganField = new TextField(); // TextField untuk input keterangan
        keteranganField.setPromptText("Masukkan Keterangan"); //setPromptText untuk memberikan petunjuk pada TextField
        keteranganField.getStyleClass().add("from-input");

        // input nominal
        nominalField = new TextField();
        nominalField.setPromptText("Masukkan Nominal");
        nominalField.getStyleClass().add("from-input");

        // pilihan tipe
        tipeBox = new ComboBox<>( // ComboBox<string> untuk membuat dropdown pilihan tipe
            FXCollections.observableArrayList("Pemasukan", "Pengeluaran")); // FXCollections.observableArrayList untuk membuat daftar pilihan dalam ComboBox
        tipeBox.setValue("Pemasukan"); // setValue untuk menetapkan nilai default pada ComboBox
        tipeBox.getStyleClass().add("from-combo");

        // pilih tanggal
        tanggalPicker = new DatePicker(); // DatePicker untuk memilih tanggal
        tanggalPicker.getStyleClass().add("from-date");
    }

    // === button ===
    private void createButton(){
        // tombol tambah
        tambahButton = new Button("Tambah");
        tambahButton.getStyleClass().add("primary-button");

        // tombol batal edit
        batalButton = new Button("Batal");
        batalButton.setVisible(false);
        batalButton.setManaged(false); // batalButton.setManaged(false) = tombolnya memang hilang, tapi ruang kosongnya masih ada.
        batalButton.getStyleClass().add("cancel-button");
    }

    // === Tabel ===
    private void createTable(){
        createTableView();
        createKeteranganColumn();
        createNominalColumn();
        createTipeColumn();
        createTanggalColumn();
        createEditColumn();
        createHapusColumn();
    }

    private void createTableView(){
        // tabel transaksi
        transaksiTable = new TableView<>(); // TableView untuk menampilkan daftar transaksi, tipe data disesuaikan dengan model transaksi yang digunakan
        transaksiTable.setItems(transaksiService.getDaftarTransaksi()); // setItems untuk menghubungkan TableView dengan data transaksi yang dikelola oleh transaksiService
        transaksiTable.getStyleClass().add("transaksi-table");
        transaksiTable.setPrefHeight(300); // setPrefHeight untuk mengatur tinggi tabel transaksi
    }
    private void createKeteranganColumn(){
        // kolom keterangan
        keteranganColumn = new TableColumn<>("Keterangan"); // TableColumn untuk kolom keterangan, tipe data String
        keteranganColumn.setCellValueFactory(new PropertyValueFactory<>("keterangan")); // setCellValueFactory untuk menghubungkan kolom dengan properti keterangan dalam model Transaksi
        keteranganColumn.setPrefWidth(250); // setPrefWidth untuk mengatur lebar kolom keterangan
    }

    private void createNominalColumn(){
        // kolom nominal
        nominalColumn = new TableColumn<>("Nominal");
        nominalColumn.setCellValueFactory(new PropertyValueFactory<>("nominal")); // PropertyValueFactory untuk menghubungkan kolom dengan properti nominal dalam model Transaksi
        nominalColumn.setPrefWidth(150);
    }

    private void createTipeColumn(){
        // kolom tipe
        tipeColumn = new TableColumn<>("Tipe");
        tipeColumn.setCellValueFactory(new PropertyValueFactory<>("tipe")); // PropertyValueFactory untuk menghubungkan kolom dengan properti tipe dalam model Transaksi
        tipeColumn.setPrefWidth(150);
    }

    private void createTanggalColumn(){
        // kolom tanggal
        tanggalColumn = new TableColumn<>("Tanggal");
        tanggalColumn.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        tanggalColumn.setPrefWidth(150);
    }

    private void createEditColumn(){
        // tombol edit didalam tabel
        editColumn = new TableColumn<>("Edit");
        editColumn.setPrefWidth(100);

        editColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(null));
    }

    private void createHapusColumn(){
        // tombol hapus di dalam tebel
        hapusColumn = new TableColumn<>("Hapus"); // TableColumn untuk kolom hapus, tipe data Void karena kolom ini hanya berisi tombol hapus, bukan data dari model Transaksi
        hapusColumn.setPrefWidth(100);

        hapusColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(null));
    }

    private void setupEditCellFactory() {
        //  logika tombol Edit didalam tabel
        editColumn.setCellFactory(param -> new TableCell<Transaksi, Void>(){
            private final Button editButton = new Button("Edit");

            {
                
                editButton.setOnAction(event -> {
                    Transaksi transaksi = getTableView().getItems().get(getIndex());
                    transaksiYangDiedit = transaksi;
                    
                    keteranganField.setText(transaksi.getKeterangan());

                    nominalField.setText(
                        String.valueOf(transaksi.getNominal())
                    );

                    tipeBox.setValue(transaksi.getTipe());

                    tanggalPicker.setValue(LocalDate.parse(transaksi.getTanggal()));

                    tambahButton.setText("Simpan Perubahan");

                    batalButton.setVisible(true);
                    batalButton.setManaged(true);
                });

                  editButton.getStyleClass().add("table-edit-button");
            }

            @Override
            protected void updateItem(Void item, boolean empty){
                super.updateItem(item, empty);
                if(empty){
                    setGraphic(null);
                }
                else{
                    setGraphic(editButton);
                    setAlignment(Pos.CENTER);
                }
            }
            
        });
    }
    
    private void setupHapusCellFactory() {
        //  logika tombol Hapus didalam tabel
        hapusColumn.setCellFactory(param -> new TableCell<Transaksi, Void>() {
            private final Button hapusButton = new Button("Hapus");
            
            { // Inisialisasi blok untuk mengatur tindakan ketika tombol hapus diklik
                hapusButton.setOnAction(event -> {
                    // Konfirmasi Hapus
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    
                    confirm.setTitle("Konfirmasi");
                    confirm.setHeaderText(null);
                    confirm.setContentText("Yakin ingin menghapus transaksi ini?");

                    // css alert hapus button
                    confirm.getDialogPane().getStylesheets().add(
                        getClass().getResource("/css/transaksi.css").toExternalForm()
                    );
                    confirm.getDialogPane().getStyleClass().add("custom-confirm");

                    Button okButton = (Button)
                                    confirm.getDialogPane()
                                    .lookupButton(ButtonType.OK);

                    okButton.getStyleClass().add("danger-button");
                    
                    Button cancelButton = (Button)
                    confirm.getDialogPane()
                    .lookupButton(ButtonType.CANCEL);
                    
                    cancelButton.getStyleClass().add("secondary-button");
                    
                    Optional<ButtonType> result = confirm.showAndWait();
                    
                    if(result.isPresent()
                        && result.get()==ButtonType.OK){
                    
                    Transaksi transaksi =
                                getTableView()
                                .getItems()
                                .get(getIndex());

                        transaksiService
                                .hapusTransaksi(transaksi);

                        updateTable();

                        dashboardView.refreshDashboard();
                    }
                });

                hapusButton.getStyleClass().add("table-delete-button");
            }

            @Override // Override untuk mengganti metode updateItem() dari TableColumn, yang digunakan untuk memperbarui tampilan sel dalam kolom hapus
            protected void updateItem(Void item, boolean empty){ // updateItem() untuk memperbarui tampilan sel dalam kolom hapus, dengan parameter item (data sel) dan empty (apakah sel kosong atau tidak)
                super.updateItem(item, empty); // super.updateItem(item, empty) untuk memanggil metode updateItem() dari kelas induk TableColumn, agar tetap mempertahankan perilaku dasar dari sel tabel
                if (empty) {
                    setGraphic(null); // setGraphic(null) untuk menghapus tampilan tombol hapus jika sel kosong, sehingga tidak ada tombol yang ditampilkan pada baris kosong dalam tabel
                } else {
                    setGraphic(hapusButton);
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

    private void showWarning(String message) {

        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Validasi");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.getDialogPane().getStylesheets().add(
            getClass().getResource("/css/transaksi.css").toExternalForm()
        );

        alert.getDialogPane().getStyleClass().add("custom-alert");

        alert.showAndWait();
    }

    private void updateTable(){
            ObservableList<Transaksi> hasil =
                searchService.search(
                    transaksiService.getDaftarTransaksi(),
                    keyword
                );
            
            hasil = filterService.filter(
                            hasil,
                            filterBox.getValue()
                    );
            
            hasil = sortService.sort(
                            hasil,
                            sortBox.getValue()
            );
            transaksiTable.setItems(hasil);

        }
    
}
