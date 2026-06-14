package storage;

import java.io.BufferedWriter; // Menulis teks ke file dengan lebih cepat dan efisien
import java.io.FileWriter; // Membuka atau membuat file untuk ditulisi data
import java.io.IOException; // Menangani error saat membaca/menulis file
import java.io.Writer;
import java.util.List; // Menyimpan banyak objek dalam satu koleksi

import model.Transaksi;

public class CSVHelper {

    private static final String FILE_PATH = "transaksi.csv"; // membuat konstanta bernama FILE_PATH yang nilainya "transaksi.csv", tidak bisa diubah, dan hanya digunakan oleh class CSVHelper.

    public static void saveToCSV(List<Transaksi> transaksisList){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("keterangan,nominal,tipe,tanggal");
            writer.newLine(); 

            for(Transaksi t : transaksisList){ // Ambil setiap objek Transaksi yang ada di dalam transaksisList, lalu simpan sementara ke variabel t
                writer.write(
                    t.getKeterangan() + "," +
                    t.getNominal() + "," +
                    t.getTipe() + "," +
                    t.getTanggal()
                );

                writer.newLine();
            }

        } catch (IOException e) { // IOException Artinya Kalau terjadi error yang berhubungan dengan proses Input/Output (file, folder, stream, dll), tangkap error tersebut.
            e.printStackTrace(); // Cetak detail error ke console.
        }
    }

    
}
