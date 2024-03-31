package oop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.io.IOException;

public class CounterController {
    @FXML private TextField item_quantity;
    @FXML private TextField item_price;
    @FXML private TextField total_price;
    @FXML private CheckBox search_switch;
    @FXML  private Label search_switch_label;
    @FXML private ComboBox<String> item_combobox;
    @FXML public TableView<Purchase> item_list;
    @FXML private Label errorItemName;
    @FXML private Label errorQuantity;
    @FXML private Text title;
    
    private int table_index = 1;
    public float totalPrice = 0.0f;

    // ObservableList<BarangList> barang = BarangList.LoadItem2();
    ObservableList<BarangList> barang = BarangList.LoadItemDb();
    ObservableList<String> items = FXCollections.observableArrayList();

    public void initialize() {
        // Initialize the items
        
        errorItemName.setTextFill(Color.RED);
        errorQuantity.setTextFill(Color.RED);

        for (BarangList row : barang){
            items.add(row.nama);
        }
        item_combobox.setItems(items);

        // bikin ngebug
        // ada yg msh ngebug
        item_combobox.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            String filterText = newValue.toLowerCase();
            ObservableList<String> filteredItems = items.filtered(item -> item.toLowerCase().startsWith(filterText));
            boolean isNotFoundExact = filteredItems.filtered(item -> item.toLowerCase().equals(filterText)).size() == 0;
            if (filterText.length() != 0 && isNotFoundExact){
                item_combobox.setItems(filteredItems);
            }
            else {
                item_combobox.setItems(items);
                // System.out.println("ITEM : " +item_combobox.getItems());
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

    }   

    @FXML
    private void tambahkan(ActionEvent event){
        String namaItem = item_combobox.getValue();
        if(namaItem.length()!=0){
            try {
                float harga = findObjectFromNama(namaItem).harga;
                int jumlah = Integer.parseInt(item_quantity.getText());
                float total = harga * jumlah;
                String kode = findObjectFromNama(namaItem).kode;

                item_list.getItems().add(new Purchase(table_index, kode, namaItem, harga, jumlah, total));
                table_index++;
                errorItemName.setText("");
                errorQuantity.setText("");
            }
            catch (NullPointerException nullError){
                System.out.println("Maaf, untuk barang " + namaItem + " tidak tersedia");
                errorItemName.setText("Maaf, untuk barang " + namaItem + " tidak tersedia");
            }
            
            catch (NumberFormatException numError){
                System.out.println("Barang tersedia, jumlah salah");
                errorQuantity.setText("Barang tersedia, jumlah salah");
            }
        }
    }

    private void calculateTotalPrice(){
        float harga;
        String jumlah;
        jumlah = item_quantity.getText();
        try{
            harga = Float.parseFloat(item_price.getText());
            total_price.setText(Float.toString(Integer.parseInt(jumlah) * harga));
            errorQuantity.setText("");
        }
        catch (NumberFormatException numError){
            System.out.println("gagal mendapatkan barang sebanyak " + jumlah + " ekor");
            errorQuantity.setText("gagal mendapatkan barang sebanyak " + jumlah + " ekor");
        }
    }

    private BarangList findObjectFromNama(String nama){
        for (BarangList row: barang){
            if (row.nama.equals(nama)){
                return row;
            }
        }
        // System.out.println("no " + nama);
        throw new NullPointerException("no nama");
    }

    @FXML
    private void search_for_prices(ActionEvent event) {
        // System.out.println("Im clean");
        String selectedItem = item_combobox.getValue();
        if (selectedItem != null){
            try{
                float harga = findObjectFromNama(selectedItem).harga;
                item_price.setText(Float.toString(harga));
                calculateTotalPrice();
                errorItemName.setText("");
            }
            catch (NullPointerException nullerror){
                System.out.println("barang : " + selectedItem + " tidak ditemukan harganya");
                errorItemName.setText("Barang tidak tersedia");
            }
        }
    }

    @FXML
    private void tambah(ActionEvent event) {
        calculateTotalPrice();
    }

    @FXML
    private void batal(ActionEvent event) throws IOException{
        // App.setRoot("login");
        App.setRoot("login");
    }

    @FXML
    private void bayar(ActionEvent event) throws IOException{
        float total_semua = 0;
        for (Purchase record : item_list.getItems()){
            System.out.println("Harga total dari " + record.table_nama + " adalah " + record.table_total);
            total_semua = total_semua + record.table_total;
        }
        System.out.println("Total semua : " + total_semua);
        FXMLLoader loader = App.getFXML("checkout");
        Parent parent = loader.load();
        CheckoutController check = loader.getController();
        check.setTotalPayment(total_semua);
        // System.out.println(check);
        App.setRootWithParent(parent);
        // App.getRoot();
        // item_list.getItems().clear();
        // System.out.println("Requesting GUI for total harga Mayhaps (?)");
        
        // FXMLLoader loader = App.fxmlLoader("checkout");
        // CheckoutController check = loader.getController();
        // check.setTotalPayment(2.0f);

        // App.setRoot();
        // FXMLLoader loader = new FXMLLoader(getClass().getResource("checkout.fxml"));
        // CheckoutController payment = loader.getController();

        // System.out.println(payment);
        // payment.setTotalPayment(total_semua);
        // payment.setTotalPayment(total_semua);
        // CheckoutController check = new CheckoutController();
        // check.setTotalPayment(total_semua);
        
        // total_price.setText(Float.toString(total_semua));
        // UserData user = new UserData();
        // user.totalHarga = total_semua;

        // this.totalPrice = total_semua;
        // App.totalHarga = total_semua;
        // App.setHarga(total_semua);
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

        // int a = 2;
        // float b = 1.5f;
        // System.out.println(a * b);
        
        Font font = Font.loadFont(App.class.getResourceAsStream("fonts/Montserrat.ttf"), 45.0);
        System.out.println(font);
        // System.out.println(App.class.getResourceAsStream("fonts/Montserrat.ttf"));
    }
}
