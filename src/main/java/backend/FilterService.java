package backend;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Transaksi;

public class FilterService {
    public ObservableList<Transaksi> filter(
        ObservableList<Transaksi> daftar,
        String tipe
    ){
        if(tipe.equals("Semua")){
            return daftar;
        }

        ObservableList<Transaksi> hasil =
            FXCollections.observableArrayList();

        for(Transaksi transaksi : daftar){
            if (transaksi.getTipe().equalsIgnoreCase(tipe)){
                hasil.add(transaksi);
            }

        }
        return hasil;
    }
}
