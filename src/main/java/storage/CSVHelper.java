package storage;

import java.io.BufferedWriter; // Menulis teks ke file dengan lebih cepat dan efisien
import java.io.FileWriter; // Membuka atau membuat file untuk ditulisi data
import java.io.IOException; // Menangani error saat membaca/menulis file
import java.io.Writer; // Writer adalah kelas induk (parent class) untuk semua class yang digunakan menulis data teks.
import java.util.List; // Menyimpan banyak objek dalam satu koleksi
import java.io.BufferedReader; // Fungsinya untuk membaca teks dengan lebih efisien, biasanya per baris.
import java.io.FileReader; // Fungsinya untuk membuka dan membaca file teks.
import java.util.ArrayList; // Mengimpor class ArrayList. Fungsinya untuk menyimpan banyak data dalam bentuk list yang ukurannya bisa bertambah atau berkurang.

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

    public static List<Transaksi> loadFromCSV(){
        List<Transaksi> transaksiList =  new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))){ // Buka file FILE_PATH, buat BufferedReader bernama reader, lalu tutup otomatis setelah selesai digunakan.
            
            // Lewati header
            reader.readLine(); // Baca satu baris teks dari file.

            String line;

            while ((line = reader.readLine()) != null) { // Baca satu baris dari file, simpan ke variabel line, lalu selama hasilnya bukan null, jalankan isi loop
                String[] data = line.split(",");

                Transaksi transaksi = new Transaksi(
                        data[0], 
                        Double.parseDouble(data[1]), 
                        data[2], 
                        data[3]
                );
                
                transaksiList.add(transaksi);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return transaksiList;
            
    }

    
}
