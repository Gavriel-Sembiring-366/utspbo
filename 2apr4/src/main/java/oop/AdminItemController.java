package oop;

import java.io.IOException;
import java.sql.PreparedStatement;
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
import javafx.scene.input.MouseEvent;


public class AdminItemController {
    
    @FXML public TableView<Purchase> tableViewItem;

    @FXML private TextField textFieldKode;
    @FXML private TextField textFieldNama;
    @FXML private TextField textFieldHarga;
    @FXML private Label labelTitle;
    @FXML private Label labelKode;
    @FXML private Label labelNama;
    @FXML private Label labelHarga;
    @FXML private Label labelView;
    @FXML private Label labelDescription;
    @FXML private Button buttonTambah;
    @FXML private Button buttonEdit;
    @FXML private Button buttonHapus;
    @FXML private Button buttonBatal;
    @FXML private Button buttonSwitch;

    public void initialize(){
        
        ObservableList<Label> labels = FXCollections.observableArrayList(
            labelKode, 
            labelNama, 
            labelHarga,
            labelView,
            labelDescription
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
            buttonSwitch
        );

        labels.forEach(label -> {label.fontProperty().set(App.Montserrat(14.0));
            
        });
        buttons.forEach(button->{button.fontProperty().set(App.Montserrat(18.0));});
        textFields.forEach(textField ->{textField.fontProperty().set(App.Montserrat(12.0));});
        labelTitle.fontProperty().set(App.Montserrat(56.0));

        tableViewItem.getColumns().addAll(Item.getItemColumns("admin"));
        loadItemTable();
    }

    private void loadItemTable(){
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

        tableViewItem.setOnMouseClicked((MouseEvent event)->{
            int index = tableViewItem.getSelectionModel().getSelectedIndex();
            Purchase row = tableViewItem.getItems().get(index);
            // System.out.println(row.table_nama);
            textFieldKode.setText(row.table_kode);
            textFieldNama.setText(row.table_nama);
            textFieldHarga.setText(App.floatThousandSeparator(row.table_harga));
        });
    }

    public boolean kodeIsUsed(String kode) throws SQLException{
        DBConnector.initDBConnection();
        try {
            if(!kode.equals("")){
                PreparedStatement stmt = DBConnector.connect.prepareStatement(
                    "SELECT id FROM item WHERE id LIKE ?"
                );
                stmt.setString(1, kode);
                ResultSet rs = stmt.executeQuery();  
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
                    sql = "INSERT INTO item (id, nama, harga) VALUES (?, ?, ?)" ;
                    _errorMsg = "Kode kosong atau sudah digunakan pada produk lain.";
                    _successMsg = "Barang telah ditambah.";
                    break;

                case "edit":
                    sql = "UPDATE item SET nama = ?, harga = ? WHERE id LIKE ?";
                    _errorMsg = "Kode tidak ada.";
                    _successMsg = "Barang telah diedit.";
                    break;

                case "hapus":
                    sql = "DELETE FROM item WHERE id LIKE ?";
                    _errorMsg = "Kode tidak ada.";
                    _successMsg = "Barang telah dihapus.";
                    break;
            }

            if((kodeIsUsed(kode) == (args !="tambah")) || kode.equals("")){
                System.out.println(_errorMsg);
            }
            else{
                if(nama.replaceAll(" ","").equals("") || harga.equals("0")){
                    System.out.println("Isilah data dengan lengkap.");
                }
                else{
                    PreparedStatement psmt = DBConnector.connect.prepareStatement(sql);
                    switch(args){
                        case "tambah":
                            psmt.setInt(1, Integer.parseInt(kode));
                            psmt.setString(2, nama);
                            psmt.setFloat(3, Float.parseFloat(harga));
                            break;
        
                        case "edit":
                            psmt.setString(1, nama);
                            psmt.setFloat(2, Float.parseFloat(harga));
                            psmt.setInt(3, Integer.parseInt(kode));
                            break;
        
                        case "hapus":
                            psmt.setInt(1, Integer.parseInt(kode));
                            break;
                    }
                    psmt.executeUpdate();
                    System.out.println(_successMsg);
                    loadItemTable();
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

    @FXML
    private void actionSwitch() throws IOException {
        App.setRoot("adminActLog");
    }
}
