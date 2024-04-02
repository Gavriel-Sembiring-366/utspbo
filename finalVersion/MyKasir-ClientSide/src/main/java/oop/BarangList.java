package oop;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BarangList {
    public String kode;    
    public String nama;    
    public float harga;
    public BarangList(){
        this.kode = "kode";
        this.nama = "nama";
        this.harga = 0;
    }
    public BarangList(String kode, String nama, float harga){
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
    }
    
    public static ObservableList<BarangList> LoadItemDb(){
        DBConnector.initDBConnection();
        ArrayList<Item> loadItem = Item.loadItemFromDB();

        ObservableList<BarangList> list_of_item = FXCollections.observableArrayList();
        for(Item itemClass : loadItem){
            BarangList barangN = new BarangList(itemClass.id, itemClass.nama, itemClass.harga);
            list_of_item.add(barangN);
        }
        return list_of_item;
    }
}