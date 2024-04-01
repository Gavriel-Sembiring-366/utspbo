package oop;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


public class AdminController {
    
    @FXML public TableView<Purchase> tableViewItem;
    @FXML private TextField textFieldKode;
    @FXML private TextField textFieldNama;
    @FXML private TextField textFieldHarga;
    @FXML private Label labelTitle;
    @FXML private Label labelKode;
    @FXML private Label labelNama;
    @FXML private Label labelHarga;
    @FXML private Button buttonTambah;
    @FXML private Button buttonEdit;
    @FXML private Button buttonHapus;
    @FXML private Button buttonBatal;
    @FXML private Button buttonBayar;

    public void initialize(){

        ObservableList<Label> labels = FXCollections.observableArrayList(
            labelKode, 
            labelNama, 
            labelHarga
        );

        ObservableList<TextField> textFields = FXCollections.observableArrayList(
            textFieldNama,
            textFieldKode,
            textFieldHarga
        );

        ObservableList<Button> buttons = FXCollections.observableArrayList(
            buttonTambah,
            buttonEdit,
            buttonHapus,
            buttonBatal,
            buttonBayar
        );

        labels.forEach(label -> {label.fontProperty().set(App.Montserrat(14.0));
            
        });
        buttons.forEach(button->{button.fontProperty().set(App.Montserrat(18.0));});
        textFields.forEach(textField ->{textField.fontProperty().set(App.Montserrat(12.0));});
        labelTitle.fontProperty().set(App.Montserrat(56.0));

        tableViewItem.getColumns().addAll(Item.getItemColumns("admin"));
        
        try {
            DBConnector.initDBConnection();
            Statement stmt = DBConnector.connect.createStatement();
            String sql = "SELECT * FROM item";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next())
            {
                Purchase index = new Purchase();
                index.table_kode = Integer.toString(rs.getInt("id"));
                index.table_nama = rs.getString("nama");
                index.table_harga = rs.getFloat("harga");

                tableViewItem.getItems().add(index);
            }
        }
        catch (Exception e)
        {
            System.out.println("Error :" + e);
        }
    }

    public boolean kodeIsUsed(String kode) throws SQLException{
        DBConnector.initDBConnection();
        try {
            if(!kode.equals("")){
                Statement stmt = DBConnector.connect.createStatement();
                String sql = "SELECT id FROM item WHERE id LIKE '" + Integer.parseInt(kode) + "'";
                ResultSet rs = stmt.executeQuery(sql);  
                return !rs.next();
            }
        }
        catch (NumberFormatException numError){
            System.out.println("NumError : " + numError);
        }
        return false;
    }

    public void buttonActions(String args) throws SQLException{
        try{
            String kode = textFieldKode.getText();
            String nama = textFieldNama.getText();
            String harga = textFieldHarga.getText();
            String sql = "";
            String _errorMsg = "";
            String _successMsg = "";
            switch(args){
                case "tambah":
                    sql = "INSERT INTO item (id, nama, harga) VALUES (" + Integer.parseInt(kode) + ",'" + nama + "', " + Float.parseFloat(harga) +")" ;
                    _errorMsg = "Kode sudah digunakan pada produk lain.";
                    _successMsg = "Barang telah ditambah.";
                    break;

                case "edit":
                    sql = "UPDATE item SET nama = '" + nama +"', harga = " + Float.parseFloat(harga) + " WHERE id LIKE " + Integer.parseInt(kode);
                    _errorMsg = "Kode tidak ada.";
                    _successMsg = "Barang telah diedit.";
                    break;

                case "hapus":
                    sql = "DELETE FROM item WHERE id LIKE '" + Integer.parseInt(kode) +"'";
                    _errorMsg = "Kode tidak ada.";
                    _successMsg = "Barang telah dihapus.";
                    break;
            }
            
            if(kodeIsUsed(kode) == (args !="tambah")){
                System.out.println(_errorMsg);
            }
            else{
                if(nama.equals("") || harga.equals("")){
                    System.out.println("Isilah data dengan lengkap.");
                }
                else{
                    Statement stmt = DBConnector.connect.createStatement();
                    stmt.executeUpdate(sql);
                    System.out.println(_successMsg);
                }
            }
        }
        catch(NumberFormatException numError){
            System.out.println("Masukkan angka dengan benar.");
        }
    }

    @FXML
    private void actionExit() throws IOException{
        App.setRoot("login");
    }

    @FXML 
    private void actionTambah() throws IOException, SQLException{
        buttonActions("tambah");
    }

    @FXML
    private void actionEdit() throws IOException, SQLException{
        buttonActions("edit");
    }

    @FXML 
    private void actionHapus() throws IOException, SQLException{
        buttonActions("hapus");
    }
}
