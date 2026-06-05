package frontend.view;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class TransaksiView extends VBox { // extends VBox untuk membuat layout vertikal
    public TransaksiView(){
        // label
        Label title = new Label("Tambah Transaksi");

        // input keterangan
        TextField keteranganField = new TextField(); // TextField untuk input keterangan
        keteranganField.setPromptText("Masukkan Keterangan"); //setPromptText untuk memberikan petunjuk pada TextField
    
        // input nominal
        TextField nominalField = new TextField();
        nominalField.setPromptText("Masukkan Nominal");

        // pilihan tipe
        ComboBox<String> tipeBox = new ComboBox<>( // ComboBox<string> untuk membuat dropdown pilihan tipe
            FXCollections.observableArrayList("Pemasukan", "Pengeluaran") // FXCollections.observableArrayList untuk membuat daftar pilihan dalam ComboBox
        );

        tipeBox.setValue("Pemasukan"); // setValue untuk menetapkan nilai default pada ComboBox

        // pilih tanggal
        DatePicker tanggalPicker = new DatePicker(); // DatePicker untuk memilih tanggal

        // tombol tambah
        Button tambahButton = new Button("Tambah");

        // tabel transaksi
        TableView<?> transaksiTable = new TableView<>(); // TableView untuk menampilkan daftar transaksi, tipe data disesuaikan dengan model transaksi yang digunakan
        // ? di Java disebut wildcard generic = TableView ini menyimpan objek apa saja, saya belum menentukan tipenya
    
        // layout
        setSpacing(10); // setSpacing untuk memberikan jarak antar elemen dalam VBox
        getChildren().addAll(
            title,
            keteranganField,
            nominalField,
            tipeBox,
            tanggalPicker,
            tambahButton,
            transaksiTable
        );
    }
    
}
