package oop;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Item {
    public String id;
    public String nama;
    public float harga;

    static ArrayList<Item> daftarBarang;

    public static void loadItemFromDB()
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
            System.out.println(e);
        }
    }
}
