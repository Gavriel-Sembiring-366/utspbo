package oop;

// import oop.DBConnector;
// import oop.ActivityLog;
import javafx.beans.property.SimpleStringProperty;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ActivityLogDB {

    static ArrayList<ActivityLog> ActLog;

    public void initialize(){
        // DBConnector.initDBConnection();
    }

    public static ArrayList<ActivityLog> fetchAllActivityLog() {
        ActLog = new ArrayList<>();
        try (Statement statement = DBConnector.connect.createStatement()) {
            String query = "SELECT * FROM activity_log";
            ResultSet result = statement.executeQuery(query);

            while (result.next()) {
                ActivityLog actLog = new ActivityLog();
                // match column names with local DB
                int idAct = result.getInt("id_act"); 
                int idUser = result.getInt("id_user");
                String dateTimeString = result.getString("date");
                String username = result.getString("username");
                String typeAct = result.getString("tipe_act");

                SimpleStringProperty dateProperty = new SimpleStringProperty();
                SimpleStringProperty timeProperty = new SimpleStringProperty();
                separateDateTime(dateTimeString, dateProperty, timeProperty);

                String dateString = dateProperty.get();
                String timeString = timeProperty.get();

                actLog.setDate(dateString);
                actLog.setIdAct(idAct);
                actLog.setIdUser(idUser);
                actLog.setTime(timeString);
                actLog.setUsername(username);
                actLog.setTypeAct(typeAct);
                actLog.setDesc(convertTypeActToDesc(typeAct));
                ActLog.add(actLog);
            }

            return ActLog;

        } catch (SQLException e) {
            System.out.println("Error : " + e);
            throw new RuntimeException(e);
        }
    }

    public static void separateDateTime(String dateTimeString, SimpleStringProperty dateProperty, SimpleStringProperty timeProperty) {
        // Pisahkan tanggal dan waktu
        String[] parts = dateTimeString.split(" ");
        String dateString = parts[0];
        String timeString = parts[1];

        // Set properti tanggal dan waktu
        dateProperty.set(dateString);
        timeProperty.set(timeString);
    }

    public static void clearActivityLog() {
        try (Statement statement = DBConnector.connect.createStatement()) {
            String clearQuery = "DELETE FROM activity_log";
            statement.executeUpdate(clearQuery);
            System.out.println("Activity log table cleared successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insertActivityLog(ActivityLog actLog) {
        try (PreparedStatement insertStatement = DBConnector.connect.prepareStatement(
                "INSERT INTO activity_log (id_user, username, tipe_act, date) VALUES (?, ?, ?, ?)")) {

            // insertStatement.setInt(1, actLog.getIdAct());
            insertStatement.setInt(1, actLog.getIdUser());
            insertStatement.setString(2, actLog.getUsername());
            insertStatement.setString(3, actLog.getTypeAct());
            insertStatement.setString(4, actLog.getDate());
            insertStatement.executeUpdate();
            System.out.println("Activity log inserted successfully.");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String convertTypeActToDesc(String typeAct) {
        String description = "";
        switch (typeAct) {
            case "LI":
                description = "Login / Logout";
                break;
            case "TR":
                description = "Transaction Added";
                break;
            case "AC":
                description = "Account Created";
                break;
            default:
                description = typeAct;
        }
        return description;
    }

    public static List<ActivityLog> getFilteredActivityLogs(String selectedDate){
        List<ActivityLog> logs = new ArrayList<>();
        try (PreparedStatement statement = DBConnector.connect.prepareStatement(
                "SELECT * FROM activity_log WHERE DATE(date) = ?")) {
            statement.setString(1, String.valueOf(selectedDate));

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                ActivityLog actLog = new ActivityLog();

                int idAct = rs.getInt("id_act");
                int idUser = rs.getInt("id_user");
                String dateTimeString = rs.getString("date");
                String username = rs.getString("username");
                String typeAct = rs.getString("tipe_act");

                SimpleStringProperty dateProperty = new SimpleStringProperty();
                SimpleStringProperty timeProperty = new SimpleStringProperty();
                separateDateTime(dateTimeString, dateProperty, timeProperty);

                String dateString = dateProperty.get();
                String timeString = timeProperty.get();

                actLog.setDate(dateString);
                actLog.setIdAct(idAct);
                actLog.setIdUser(idUser);
                actLog.setTime(timeString);
                actLog.setUsername(username);
                actLog.setTypeAct(typeAct);
                actLog.setDesc(convertTypeActToDesc(typeAct));
                logs.add(actLog);
            }

            rs.close();
        }
        catch (SQLException sqlError){
            System.out.println(sqlError);
        }
        return logs;
    }
    public static void main(String args[]){
        System.out.println("OK");
        DBConnector.initDBConnection();
        // System.out.println((new Date(System.currentTimeMillis())).toString());
        // insertActivityLog(new ActivityLog(1, 1, "2024-04-01 10:00", "zz", "a", "LI", ""));
        // insertActivityLog(new ActivityLog(1, "2024-04-01 11:00", "a", "LI"));
        // System.out.println(fetchAllActivityLog()); 
        // fetchAllActivityLog().forEach(log-> {
        //     System.out.println(log.getDesc());
        // }); 
        // System.out.println(getFilteredActivityLogs(LocalDate.now()));
        // System.out.println(getFilteredActivityLogs("2024-04-02"));
        // System.out.println(LocalDate.now());
        getFilteredActivityLogs("2024-04-01").forEach(log->{System.out.println(log.getTime());});

        // DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        // LocalDateTime now= LocalDateTime.now();
        // System.out.println(dtf.format(now));
    }
}