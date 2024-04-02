package oop;

import java.sql.*;
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

                actLog.setDate(dateTimeString);
                actLog.setIdAct(idAct);
                actLog.setIdUser(idUser);
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
            case "TR YES":
                description = "Transaction Success";
                break;
            case "TR NO":
                description = "Transaction Cancelled";
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


                actLog.setDate(dateTimeString);
                actLog.setIdAct(idAct);
                actLog.setIdUser(idUser);
                actLog.setUsername(username);
                actLog.setTypeAct(typeAct);
                logs.add(actLog);
            }
            rs.close();
        }
        catch (SQLException sqlError){
            System.out.println(sqlError);
        }
        return logs;
    }
}