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

    @SuppressWarnings("unchecked")
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

        TableColumn<ActivityLog, Integer> logIdLogColumn = new TableColumn<ActivityLog, Integer>("Log ID");
        logIdLogColumn.setCellValueFactory(new PropertyValueFactory<ActivityLog, Integer>("idAct"));

        TableColumn<ActivityLog, Integer> userIdLogColumn = new TableColumn<ActivityLog, Integer>("User ID");
        userIdLogColumn.setCellValueFactory(new PropertyValueFactory<ActivityLog, Integer>("idAct"));

        TableColumn<ActivityLog, String> dateLogColumn = new TableColumn<ActivityLog, String>("Date & Time");
        dateLogColumn.setCellValueFactory(new PropertyValueFactory<ActivityLog, String>("date"));

        TableColumn<ActivityLog, String> usernameLogColumn = new TableColumn<ActivityLog, String>("Username");
        usernameLogColumn.setCellValueFactory(new PropertyValueFactory<ActivityLog, String>("username"));

        TableColumn<ActivityLog, String> typeLogColumn = new TableColumn<ActivityLog, String>("Log Type");
        typeLogColumn.setCellValueFactory(new PropertyValueFactory<ActivityLog, String>("typeAct"));

        if(mode.equals("counter") || mode.equals("receipt")){
            columns.addAll(noColumn, kodeColumn, namaColumn, hargaColumn, jumlahColumn, totalColumn);
        }
        else if(mode.equals("admin")){
            columns.addAll(kodeColumn, namaColumn, hargaColumn);
        }
        return columns;
    }

    @SuppressWarnings("unchecked")
    public static ObservableList<TableColumn<ActivityLog,?>> getLogColumns(String mode){
        ObservableList<TableColumn<ActivityLog,?>> logColumns = FXCollections.observableArrayList();
        TableColumn<ActivityLog, Integer> logIdLogColumn = new TableColumn<ActivityLog, Integer>("Log ID");
        logIdLogColumn.setCellValueFactory(new PropertyValueFactory<ActivityLog, Integer>("idAct"));

        TableColumn<ActivityLog, Integer> userIdLogColumn = new TableColumn<ActivityLog, Integer>("User ID");
        userIdLogColumn.setCellValueFactory(new PropertyValueFactory<ActivityLog, Integer>("idAct"));

        TableColumn<ActivityLog, String> dateLogColumn = new TableColumn<ActivityLog, String>("Date & Time");
        dateLogColumn.setCellValueFactory(new PropertyValueFactory<ActivityLog, String>("date"));

        TableColumn<ActivityLog, String> usernameLogColumn = new TableColumn<ActivityLog, String>("Username");
        usernameLogColumn.setCellValueFactory(new PropertyValueFactory<ActivityLog, String>("username"));

        TableColumn<ActivityLog, String> typeLogColumn = new TableColumn<ActivityLog, String>("Log Type");
        typeLogColumn.setCellValueFactory(new PropertyValueFactory<ActivityLog, String>("typeAct"));

        if(mode.equals("admin_log")){
            logColumns.addAll(logIdLogColumn, userIdLogColumn, dateLogColumn, usernameLogColumn, typeLogColumn);
            // return logColumns;
        }
        return logColumns;
    }

    @SuppressWarnings("unchecked")
    public static ObservableList<TableColumn<TransactionLog,?>> getTransLogColumns(String mode){
        ObservableList<TableColumn<TransactionLog,?>> logColumns = FXCollections.observableArrayList();
        TableColumn<TransactionLog, Integer> transIdLogColumn = new TableColumn<TransactionLog, Integer>("Transaction Log ID");
        transIdLogColumn.setCellValueFactory(new PropertyValueFactory<TransactionLog, Integer>("transactionID"));

        TableColumn<TransactionLog, Integer> userIdLogColumn = new TableColumn<TransactionLog, Integer>("User ID");
        userIdLogColumn.setCellValueFactory(new PropertyValueFactory<TransactionLog, Integer>("userID"));

        TableColumn<TransactionLog, String> dateLogColumn = new TableColumn<TransactionLog, String>("Date & Time");
        dateLogColumn.setCellValueFactory(new PropertyValueFactory<TransactionLog, String>("date"));

        TableColumn<TransactionLog, String> usernameLogColumn = new TableColumn<TransactionLog, String>("Username");
        usernameLogColumn.setCellValueFactory(new PropertyValueFactory<TransactionLog, String>("username"));

        TableColumn<TransactionLog, String> itemLogColumn = new TableColumn<TransactionLog, String>("Item");
        itemLogColumn.setCellValueFactory(new PropertyValueFactory<TransactionLog, String>("item"));

        TableColumn<TransactionLog, Float> totalLogColumn = new TableColumn<TransactionLog, Float>("Total");
        totalLogColumn.setCellValueFactory(new PropertyValueFactory<TransactionLog, Float>("total"));

        if(mode.equals("admin_trans_log")){
            logColumns.addAll(transIdLogColumn, userIdLogColumn, dateLogColumn, usernameLogColumn, itemLogColumn, totalLogColumn);
            // return logColumns;
        }
        return logColumns;
    }


    public static ArrayList<Item> loadItemFromDB()
    {
        daftarBarang = new ArrayList<Item>();
        Item item;
        try {
            Statement stmt = DBConnector.connect.createStatement();
            String sql = "SELECT * FROM item";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                item = new Item();
                // change column label based on your database
                item.id = Integer.toString(rs.getInt("id"));
                item.nama = rs.getString("nama");
                item.harga = rs.getFloat("harga");

                daftarBarang.add(item);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error :" + e);
        }
        return daftarBarang;
    }
    public static void main(String[] args){
    }

}