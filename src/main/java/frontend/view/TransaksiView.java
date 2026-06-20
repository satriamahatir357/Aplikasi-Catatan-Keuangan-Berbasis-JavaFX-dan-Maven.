package frontend.view;

import javafx.collections.FXCollections;
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

import java.time.LocalDate;
import java.util.Optional;

import backend.TransaksiService; // Mengimpor kelas TransaksiService dari package backend, yang merupakan layanan untuk mengelola transaksi yang akan digunakan dalam view ini
import model.Transaksi; // Mengimpor kelas Transaksi dari package model, yang merupakan model data untuk transaksi yang akan digunakan dalam

public class TransaksiView extends VBox { // extends VBox untuk membuat layout vertikal
    
    private TransaksiService transaksiService; // Deklarasi variabel transaksiService untuk mengelola transaksi dalam view ini
    private DashboardView dashboardView;
    private Transaksi transaksiYangDiedit = null;

    public TransaksiView(TransaksiService transaksiService, DashboardView dashboardView){
         this.transaksiService = transaksiService;
         this.dashboardView = dashboardView;

        // label
        Label title = new Label("Tambah Transaksi");
        title.getStyleClass().add("from-title");

        // input keterangan
        TextField keteranganField = new TextField(); // TextField untuk input keterangan
        keteranganField.setPromptText("Masukkan Keterangan"); //setPromptText untuk memberikan petunjuk pada TextField
        keteranganField.getStyleClass().add("from-input");
        
        // input nominal
        TextField nominalField = new TextField();
        nominalField.setPromptText("Masukkan Nominal");
        nominalField.getStyleClass().add("from-input");

        // pilihan tipe
        ComboBox<String> tipeBox = new ComboBox<>( // ComboBox<string> untuk membuat dropdown pilihan tipe
            FXCollections.observableArrayList("Pemasukan", "Pengeluaran") // FXCollections.observableArrayList untuk membuat daftar pilihan dalam ComboBox
        );

        tipeBox.setValue("Pemasukan"); // setValue untuk menetapkan nilai default pada ComboBox
        tipeBox.getStyleClass().add("from-combo");
        
        // pilih tanggal
        DatePicker tanggalPicker = new DatePicker(); // DatePicker untuk memilih tanggal
        tanggalPicker.getStyleClass().add("from-date");

        // tombol tambah
        Button tambahButton = new Button("Tambah");
        tambahButton.getStyleClass().add("primary-button");

        // tombol edit 
        Button editButton = new Button("Edit");
        editButton.getStyleClass().add("edit-button");

        // tombol batal edit
        Button batalButton = new Button("Batal");
        batalButton.setVisible(false);
        batalButton.setManaged(false); // batalButton.setManaged(false) = tombolnya memang hilang, tapi ruang kosongnya masih ada.
        batalButton.getStyleClass().add("cancel-button");

        // tombol hapus
        Button hapusButton = new Button("Hapus");
        hapusButton.getStyleClass().add("danger-button");

        // tabel transaksi
        TableView<Transaksi> transaksiTable = new TableView<>(); // TableView untuk menampilkan daftar transaksi, tipe data disesuaikan dengan model transaksi yang digunakan
        transaksiTable.setItems(transaksiService.getDaftarTransaksi()); // setItems untuk menghubungkan TableView dengan data transaksi yang dikelola oleh transaksiService
        transaksiTable.getStyleClass().add("transaksi-table");

        // kolom keterangan
        TableColumn<Transaksi, String> keteranganColumn = new TableColumn<>("Keterangan"); // TableColumn untuk kolom keterangan, tipe data String
        keteranganColumn.setCellValueFactory(new PropertyValueFactory<>("keterangan")); // setCellValueFactory untuk menghubungkan kolom dengan properti keterangan dalam model Transaksi
        keteranganColumn.setPrefWidth(250); // setPrefWidth untuk mengatur lebar kolom keterangan

        // kolom nominal
        TableColumn<Transaksi, Double> nominalColumn = new TableColumn<>("Nominal");
        nominalColumn.setCellValueFactory(new PropertyValueFactory<>("nominal")); // PropertyValueFactory untuk menghubungkan kolom dengan properti nominal dalam model Transaksi
        nominalColumn.setPrefWidth(150);

        // kolom tipe
        TableColumn<Transaksi, String> tipeColumn = new TableColumn<>("Tipe");
        tipeColumn.setCellValueFactory(new PropertyValueFactory<>("tipe")); // PropertyValueFactory untuk menghubungkan kolom dengan properti tipe dalam model Transaksi
        tipeColumn.setPrefWidth(150);
        // kolom tanggal
        TableColumn<Transaksi, String> tanggalColumn = new TableColumn<>("Tanggal");
        tanggalColumn.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        tanggalColumn.setPrefWidth(150);
        
        transaksiTable.setPrefHeight(300); // setPrefHeight untuk mengatur tinggi tabel transaksi
        
        // Empty State Table
        Label emptyLabel = new Label("Belum ada transaksi");
        emptyLabel.getStyleClass().add("empty-table-label");

        transaksiTable.setPlaceholder(emptyLabel);
        
        transaksiTable.getColumns().addAll(keteranganColumn, nominalColumn, tipeColumn, tanggalColumn); // Menambahkan kolom-kolom ke dalam tabel transaksi

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
            batalButton,
            editButton,
            hapusButton
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

        VBox tableCard = new VBox(
            tableTitle,
            transaksiTable
        );

        tableCard.getStyleClass().add("table-card");

        VBox content = new VBox(
            formCard,
            tableCard
        );

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
            dashboardView.refreshDashboard(); 

            // scroll ui
            keteranganField.clear();
            nominalField.clear();
            tanggalPicker.setValue(null);
            tipeBox.setValue("Pemasukan");
        
        });

        // === Hapus Button ===
        hapusButton.setOnAction(e ->{
            // Ambil transaksi yang dipilih
            Transaksi selected = transaksiTable.getSelectionModel() //Ambil objek yang bertugas mengelola pemilihan (selection) pada TableView.
                                .getSelectedItem(); // Ambil data/baris yang sedang dipilih

            // validasi
            if(selected == null){
                showWarning("Pilih transaksi terlebih dahulu");
                return;
            }

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

            // TAMPILKAN POPUP
            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && 
                result.get() == ButtonType.OK) { // Kalau user memilih sesuatu DAN pilihannya adalah OK maka jalankan proses hapus.
                    transaksiService.hapusTransaksi(selected); // memanggil method hapusTransaksi dari transaksiService
                    dashboardView.refreshDashboard(); // memanggi method dari dashboardView
                }

            });

            // === tombol edit ===
            editButton.setOnAction(e -> {
                Transaksi selected = transaksiTable.getSelectionModel()
                                    .getSelectedItem();

                 // Validasi
                if(selected == null){
                    showWarning("Pilih transaksi terlebih dahulu");
                    return;
                }
                transaksiYangDiedit = selected;
                tambahButton.setText("Simpan Perubahan");
                batalButton.setVisible(true);
                batalButton.setManaged(true);

                 // Isi form dengan data transaksi yang dipilih
               keteranganField.setText(selected.getKeterangan());
               
               nominalField.setText(String.valueOf(selected.getNominal()));

               tipeBox.setValue(selected.getTipe());

               tanggalPicker.setValue(LocalDate.parse(selected.getTanggal()));

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
    
}
