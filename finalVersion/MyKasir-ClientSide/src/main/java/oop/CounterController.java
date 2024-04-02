package oop;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

import java.io.IOException;

public class CounterController {
    @FXML private TextField textFieldQuantity;
    @FXML private TextField textFieldPrice;
    @FXML private TextField textFieldTotal;
    @FXML private ComboBox<String> comboBoxItem;
    @FXML public TableView<Purchase> tableViewItem;
    @FXML private Label errorItemName;
    @FXML private Label errorQuantity;
    @FXML private Label labelItem;
    @FXML private Label labelJumlah;
    @FXML private Label labelHarga;
    @FXML private Label labelTotal;
    @FXML private Text textTitle;
    @FXML private Button buttonBayar;
    @FXML private Button buttonTambah;
    @FXML private Button buttonBatal;
    
    private int table_index = 1;

    ObservableList<BarangList> barang = BarangList.LoadItemDb();
    ObservableList<String> items = FXCollections.observableArrayList();
    UserIdentity currentUser;

    public void initialize() {
        // Initialize the items
        ObservableList<Label> labels = FXCollections.observableArrayList(
            labelItem,
            labelJumlah,
            labelHarga,
            labelTotal
        );
        ObservableList<TextField> textFields = FXCollections.observableArrayList(
            textFieldQuantity,
            textFieldTotal,
            textFieldPrice
        );
        ObservableList<Button> buttons = FXCollections.observableArrayList(
            buttonBayar,
            buttonBatal,
            buttonTambah
        );

        labels.forEach(label -> {label.fontProperty().set(App.Montserrat(14.0));});
        textFields.forEach(textField ->{textField.fontProperty().set(App.Montserrat(12.0));});
        buttons.forEach(button -> {button.fontProperty().set(App.Montserrat(18.0));});
        textTitle.fontProperty().set(App.Montserrat(56));

        errorItemName.setTextFill(Color.RED);
        errorQuantity.setTextFill(Color.RED);

        for (BarangList row : barang){
            items.add(row.nama);
        }
        comboBoxItem.setItems(items);

        comboBoxItem.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            String filterText = newValue.toLowerCase();
            ObservableList<String> filteredItems = items.filtered(item -> item.toLowerCase().startsWith(filterText));
            boolean isNotFoundExact = filteredItems.filtered(item -> item.toLowerCase().equals(filterText)).size() == 0;
            if (filterText.length() != 0 && isNotFoundExact){
                comboBoxItem.setItems(filteredItems);
            }
            else {
                comboBoxItem.setItems(items);
            }
        });
        tableViewItem.getColumns().addAll(Item.getItemColumns("counter"));
    }   
    
    public void setCurrentUser(UserIdentity ui){
        currentUser = ui;
    }

    @FXML
    private void tambahkan(ActionEvent event){
        String namaItem = comboBoxItem.getValue();
        int jumlah = Integer.parseInt(textFieldQuantity.getText());
        if(namaItem.length()!=0 && jumlah>0){
            try {
                float harga = findObjectFromNama(namaItem).harga;
                float total = harga * jumlah;
                String kode = findObjectFromNama(namaItem).kode;

                tableViewItem.getItems().add(new Purchase(table_index, kode, namaItem, harga, jumlah, total));
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
        jumlah = textFieldQuantity.getText();
        try{
            harga = Float.parseFloat(textFieldPrice.getText());
            textFieldTotal.setText(Float.toString(Integer.parseInt(jumlah) * harga));
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
        throw new NullPointerException("no nama");
    }

    @FXML
    private void search_for_prices(ActionEvent event) {
        String selectedItem = comboBoxItem.getValue();
        if (selectedItem != null){
            try{
                float harga = findObjectFromNama(selectedItem).harga;
                textFieldPrice.setText(Float.toString(harga));
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
        ActivityLogDB.insertActivityLog(new ActivityLog(currentUser.getUserId(), App.getCurrentDateTime(), currentUser.getUserName(), "LI"));
        App.setRoot("login");
    }

    @FXML
    private void bayar(ActionEvent event) throws IOException{
        float total_semua = 0;
        for (Purchase record : tableViewItem.getItems()){
            total_semua = total_semua + record.table_total;
        }
        currentUser.setItemList(tableViewItem.getItems());

        FXMLLoader loader = App.getFXML("checkout");
        Parent parent = loader.load();
        CheckoutController check = loader.getController();
        check.setTotalPayment(total_semua);
        check.setCurrentUser(currentUser);

        ActivityLogDB.insertActivityLog(new ActivityLog(currentUser.getUserId(), App.getCurrentDateTime(), currentUser.getUserName(), "TR"));

        App.setRootWithParent(parent);

    }

    @FXML
    private void clear_list(ActionEvent event){
        System.out.println("list cleared");
        tableViewItem.getItems().clear();
    }
}
