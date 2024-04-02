package oop;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionLogDB {

    static ArrayList<TransactionLog> TransactionLog;

    public void initialize(){
        // DBConnector.initDBConnection();
    }

    public static ArrayList<TransactionLog> fetchAllTransactionLog() {
        TransactionLog = new ArrayList<>();
        try (Statement statement = DBConnector.connect.createStatement()) {
            String query = "SELECT * FROM transaction_log";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                TransactionLog transactionLog = new TransactionLog();
                int transactionID = result.getInt("transaction_id"); 
                int userID = result.getInt("user_id");
                String username = result.getString("username");
                String item = result.getString("item");
                String dateTime = result.getString("date");
                float total = result.getFloat("total");

                transactionLog.setTransactionID(transactionID);
                transactionLog.setUserID(userID);
                transactionLog.setUsername(username);
                transactionLog.setDate(dateTime);
                transactionLog.setItem(item);
                transactionLog.setTotal(total);

                TransactionLog.add(transactionLog);
            }

            return TransactionLog;

        } catch (SQLException e) {
            System.out.println("Error : " + e);
            throw new RuntimeException(e);
        }
    }

    public static void insertTransactionLog(TransactionLog transactionLog) {
        try (PreparedStatement insertStatement = DBConnector.connect.prepareStatement(
                "INSERT INTO transaction_log (transaction_id, user_id, username, date, item, total) VALUES (?, ?, ?, ?, ?, ?)")) {

            insertStatement.setInt(1, transactionLog.getTransactionID());
            insertStatement.setInt(2, transactionLog.getUserID());
            insertStatement.setString(3, transactionLog.getUsername());
            insertStatement.setString(4, transactionLog.getDate());
            insertStatement.setString(5, transactionLog.getItem());
            insertStatement.setString(6, Float.toString(transactionLog.getTotal()));
            insertStatement.executeUpdate();
            System.out.println("Transaction log inserted successfully.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<TransactionLog> getFilteredTransactionLogs(String selectedDate){
        List<TransactionLog> logs = new ArrayList<>();
        try (PreparedStatement statement = DBConnector.connect.prepareStatement(
                "SELECT * FROM transaction_log WHERE DATE(date) = ?")) {
            statement.setString(1, String.valueOf(selectedDate));

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                TransactionLog transactionLog = new TransactionLog();
                // match column names with local DB
                int transactionID = rs.getInt("transaction_id"); 
                int userID = rs.getInt("user_id");
                String dateTimeString = rs.getString("date");
                String username = rs.getString("username");
                String item = rs.getString("item");
                float total = rs.getFloat("total");

                transactionLog.setTransactionID(transactionID);
                transactionLog.setUserID(userID);
                transactionLog.setUsername(username);
                transactionLog.setItem(item);
                transactionLog.setTotal(total);
                transactionLog.setDate(dateTimeString);
                logs.add(transactionLog);
            }
            rs.close();
        }
        catch (SQLException sqlError){
            System.out.println(sqlError);
        }
        return logs;
    }
}