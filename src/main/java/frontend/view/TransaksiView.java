package frontend.view;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import backend.TransaksiService; // Mengimpor kelas TransaksiService dari package backend, yang merupakan layanan untuk mengelola transaksi yang akan digunakan dalam view ini
import model.Transaksi; // Mengimpor kelas Transaksi dari package model, yang merupakan model data untuk transaksi yang akan digunakan dalam

public class TransaksiView extends VBox { // extends VBox untuk membuat layout vertikal
    private TransaksiService transaksiService; // Deklarasi variabel transaksiService untuk mengelola transaksi dalam view ini

    public TransaksiView(){
        getStyleClass().add("transaksi-view"); // ini css TransaksiView
        this.transaksiService = new TransaksiService(); // Inisialisasi transaksiService
        
        // label
        Label title = new Label("Tambah Transaksi");
        title.getStyleClass().add("form-title");

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

        // layout
        setSpacing(10); // setSpacing untuk memberikan jarak antar elemen dalam VBox

        Label tableTitle = new Label("Daftar Transaksi");
        tableTitle.getStyleClass().add("form-title");

        VBox fromCard = new VBox(
            title,
            keteranganField,
            nominalField,
            tipeBox,
            tanggalPicker,
            tambahButton
        );
        fromCard.getStyleClass().add("from-card");

        VBox tableCard = new VBox(
            tableTitle,
            transaksiTable
        );
        tableCard.getStyleClass().add("table-card");

        getChildren().addAll(
            fromCard,
            tableCard
        );

        // event handler untuk tombol tambah
        tambahButton.setOnAction(e -> {
            // Ambil data dari inputan
            String keterangan = keteranganField.getText(); // Mengambil teks dari keteranganField dan menyimpannya dalam variabel keterangan
            double nominal = Double.parseDouble(nominalField.getText());
            String tipe = tipeBox.getValue(); // Mengambil nilai yang dipilih dari tipeBox dan menyimpannya dalam variabel tipe
            String tanggal = tanggalPicker.
                            getValue().
                            toString(); // Mengambil nilai yang dipilih dari tanggalPicker, mengubahnya menjadi string, dan menyimpannya dalam variabel tanggal

            Transaksi transaksi = new Transaksi(keterangan, nominal, tipe, tanggal);

            transaksiService.tambahTransaksi(transaksi); // Memanggil metode tambahTransaksi pada transaksiService untuk menambahkan transaksi baru ke dalam daftar transaksi yang dikelola oleh transaksiService
        });


    }
    
}
