package oop;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BarangList {
    public String kode;    
    public String nama;    
    public float harga;

    public static Dictionary<String, Integer> loadItem() {
        
        // predefined data, will upgrade to database soo
        Dictionary<String, Integer> list = new Hashtable<>();
        String[] item = {"Apple", "Banana", "Cherry", "Grape", "Lemon", "Orange", "Strawberry"};
        int[] harga = {20000, 10000, 15000, 17000, 29000, 19000, 30000};
        for (int i = 0 ; i< item.length ; i++){
            list.put(item[i], harga[i]);
        }
        return list;
    }

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
    
    public static ObservableList<BarangList> LoadItem2(){
        // predefined data, will upgrade to database soon
        BarangList item1 = new BarangList("1111", "Apple", 10000);
        BarangList item2 = new BarangList("1111", "Apple2", 10000);
        BarangList item3 = new BarangList("1112", "Bpple", 11000);
        BarangList item4 = new BarangList("1112", "Bpple2", 11000);
        BarangList item5 = new BarangList("1113", "Cpple", 12000);
        // BarangList item6 = new BarangList("", "", 0);
        ObservableList<BarangList> list_of_item = FXCollections.observableArrayList(item1, item2, item3, item4, item5);
        return list_of_item;
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

    public static void main(String args[]){
    //     Dictionary<String, Integer> list = new Hashtable<>();
    //     String[] item = {"Apple", "Banana", "Cherry", "Grape", "Lemon", "Orange", "Strawberry"};
    //     int[] harga = {20000, 10000, 15000, 17000, 29000, 19000, 30000};
    //     for (int i = 0 ; i< item.length ; i++){
    //         list.put(item[i], harga[i]);
    //     }
    //     System.out.println(list);
        ObservableList<BarangList> list_of_item = LoadItem2();
        System.out.println(list_of_item);
    }

}