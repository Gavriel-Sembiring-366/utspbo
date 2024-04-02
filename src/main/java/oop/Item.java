package oop;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class Item {
    public String id;
    public String nama;
    public float harga;

    static ArrayList<Item> daftarBarang;

    public static ObservableList<TableColumn<Purchase,?>> getItemColumns(String mode){
        ObservableList<TableColumn<Purchase,?>> columns = FXCollections.observableArrayList();

        TableColumn<Purchase, Integer> noColumn = new TableColumn<Purchase, Integer>("no");
        noColumn.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>("table_no"));

        TableColumn<Purchase, String> kodeColumn = new TableColumn<Purchase, String>("kode");
        kodeColumn.setCellValueFactory(new PropertyValueFactory<Purchase, String>("table_kode"));

        TableColumn<Purchase, String> namaColumn = new TableColumn<Purchase, String>("nama");
        namaColumn.setCellValueFactory(new PropertyValueFactory<Purchase, String>("table_nama"));

        TableColumn<Purchase, Integer> hargaColumn = new TableColumn<Purchase, Integer>("harga");
        hargaColumn.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>("table_harga"));

        TableColumn<Purchase, Integer> jumlahColumn = new TableColumn<Purchase, Integer>("jumlah");
        jumlahColumn.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>("table_jumlah"));
         
        TableColumn<Purchase, Integer> totalColumn = new TableColumn<Purchase, Integer>("total");
        totalColumn.setCellValueFactory(new PropertyValueFactory<Purchase, Integer>("table_total"));

        if(mode.equals("counter")){
            columns.addAll(noColumn, kodeColumn, namaColumn, hargaColumn, jumlahColumn, totalColumn);
        }
        else if(mode.equals("admin")){
            columns.addAll(kodeColumn, namaColumn, hargaColumn);
        }
        return columns;
    }

    public static ArrayList<Item> loadItemFromDB()
    {
        daftarBarang = new ArrayList<Item>();
        Item item;
        
                item = new Item();
                // change column label based on your database
                item.id = Integer.toString(1);
                item.nama = "Apple";
                item.harga = 9000;

                daftarBarang.add(item);
        return daftarBarang;
    }
    public static void main(String[] args){
        // System.out.println("ok");
        // DBConnector.initDBConnection();
        // System.out.println(loadItemFromDB());
    }

}