package oop;

//import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.io.IOException;
// import java.util.Dictionary;
// import java.util.Enumeration;
// import java.util.HashMap;
// import java.util.Map;


public class CounterController {
    @FXML
    private TextField item_quantity;

    @FXML
    private TextField item_price;

    @FXML
    private TextField total_price;

    @FXML
    private CheckBox search_switch;

    @FXML
    private Label search_switch_label;

    @FXML
    private ComboBox<String> item_combobox;

    @FXML
    public TableView<Purchase> item_list;

    // Dictionary<String, Integer> barang = BarangList.loadItem();
    // ObservableList<String> items = FXCollections.observableArrayList();
    
    // Enumeration<String> keys = barang.keys();
    private int table_index = 1;

    ObservableList<BarangList> barang = BarangList.LoadItem2();
    ObservableList<String> items = FXCollections.observableArrayList();

    public void initialize() {
        // Initialize the items
        
        // while(keys.hasMoreElements()){
        //     items.add(keys.nextElement());
        // }

        for (BarangList row : barang){
            items.add(row.nama);
        }

        item_combobox.setItems(items);

        // bikin ngebug
        item_combobox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            String filterText = newValue.toLowerCase();
            ObservableList<String> filteredItems = items.filtered(item -> item.toLowerCase().startsWith(filterText));
            if (filterText.length() != 0 && filteredItems.size() != 1 ){
                item_combobox.setItems(filteredItems);
            }
            else {
                item_combobox.setItems(items);
            }
        });

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

        item_list.getColumns().add(noColumn);
        item_list.getColumns().add(kodeColumn);
        item_list.getColumns().add(namaColumn);
        item_list.getColumns().add(hargaColumn);
        item_list.getColumns().add(jumlahColumn);
        item_list.getColumns().add(totalColumn);

        // item_list.getItems().add(new Purchase(1, "kode", "nama", 10000, 2, 20000));
        // item_list.getItems().add(new Purchase(1, "kode", "nama", 100, 2, 200));

    }   

    @FXML
    private void tambahkan(ActionEvent event){
        String nama = item_combobox.getValue();
        if(nama.length() !=0 ){
            try {
                // int harga = barang.get(nama);
                // int harga = 10;
                String harga0 = find_harga_from_nama(nama);
                // if (harga0 == null){
                //     throw new NullPointerException();
                // }
                // int harga = Integer.parseInt(harga0);
                System.out.println(harga0);
                int jumlah = Integer.parseInt(item_quantity.getText());
                int total = Integer.parseInt(total_price.getText());
                
                // item_list.getItems().add(new Purchase(table_index, "kode", nama, harga, jumlah, total));
                table_index++;
            }
            catch (NullPointerException nullError){
                System.out.println("Maaf, untuk barang " + nama + " tidak tersedia");
            }
            
            catch (NumberFormatException numError){
                System.out.println("Barang tersedia, jumlah salah");
            }
        }
    }

    private void calculateTotalPrice(){
        int harga;
        String jumlah;
        jumlah = item_quantity.getText();
        try{
            harga = Integer.parseInt(item_price.getText());
            total_price.setText(Integer.toString(Integer.parseInt(jumlah) * harga));
        }
        catch (NumberFormatException numError){
            System.out.println("gagal mendapatkan barang sebanyak " + jumlah + " ekor");
        }
    }

    private String find_harga_from_nama(String nama){
        for (BarangList row: barang){
            System.out.println(row.nama);
            if (row.nama == nama){
                System.out.println("ketemu" + nama);
                System.out.println(row.harga);
                return Integer.toString(row.harga);
                // return "10000";
            }
        }
        // throw new NullPointerException("no nama");
        System.out.println("no " + nama);
        return null;
    }

    @FXML
    private void search_for_prices(ActionEvent event) {
        String selectedItem = item_combobox.getValue();
        if (selectedItem.length() != 0){
            try{
                // int harga = barang.get(selectedItem);
                // int harga = 10;
                // int harga = find_harga_from_nama(selectedItem);
                String harga = find_harga_from_nama(selectedItem);
                // item_price.setText(Integer.toString(harga));
                item_price.setText(harga);
                calculateTotalPrice();
            }
            catch (NullPointerException nullerror){
                System.out.println("barang : " + selectedItem + " tidak ditemukan harganya");
            }
        }
    }

    @FXML
    private void tambah(ActionEvent event) {
        // String jumlah = item_quantity.getText();
        // System.out.println(jumlah);
        // int jumlah = item_quantity.getText();

        // try {
        //     calculateTotalPrice();
        // }
        // catch (NumberFormatException e){
        //     // System.out.println(e);
        //     System.out.println("Please input corrent number of item quantity");
        // }
        
        calculateTotalPrice();
    }

    @FXML
    private void batal(ActionEvent event) throws IOException{
        App.setRoot("login");
    }

    @FXML
    private void bayar(ActionEvent event){
        int total_semua = 0;
        for (Purchase record : item_list.getItems()){
            System.out.println("Harga total dari " + record.table_nama + " adalah " + record.table_total);
            total_semua = total_semua + record.table_total;
        }
        System.out.println("Total semua : " + total_semua);
        // item_list.getItems().clear();
        System.out.println("Requesting GUI for total harga Perhaps (?)");
    }

    @FXML
    private void clear_list(ActionEvent event){
        System.out.println("list cleared");
        item_list.getItems().clear();
    }

    public static void main(String args[]){
        // Map tester = new HashMap<>();
        // Map tester = new HashMap<>();
        // tester.put("Apple", 5);
        // System.out.println(tester.get("Apple"));
        // Dictionary<String, Integer> barang = BarangList.loadItem();
        // // System.out.println(barang.);
        
        // Enumeration<String> k = barang.keys();
        // Enumeration<Integer> el = barang.elements();
        
        // System.out.println(k);
        
        // while(k.hasMoreElements()){
            //         String key = k.nextElement();
            //         System.out.println(key + " :  " + barang.get(key));
            //     }
        // System.out.println("Barang " + barang);
        
        // System.out.println("size : " +barang.size());
        // System.out.println(k.nextElement());
        // System.out.println(el.nextElement());

        // int harga[] = {1,2,3};
        // for (int x : harga){
        //     System.out.println(x);
        // }
        
        // while(k.hasMoreElements()){
        //     String key = k.nextElement();
        //     System.out.println(barang.get(key));
        // }
    }
}
