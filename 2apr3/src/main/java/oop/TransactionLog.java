package oop;

public class TransactionLog {
    public int userID;
    public int transactionID;
    public String date;
    public String time;
    public String username;
    public String item;
    public float total;

    public TransactionLog(int userID, int transactionID, String date, String time, String username, String item, float total)
    {
        this.userID = userID;
        this.transactionID = transactionID;
        this.date = date;
        this.time = time;
        this.username = username;
        this.item = item;
        this.total = total;
    }
    public TransactionLog(int userID, String date, String username, String item, float total)
    {
        this.userID = userID;
        this.date = date;
        this.username = username;
        this.item = item;
        this.total = total;
    }
    public TransactionLog(){}
    
    public int getTransactionID() {return transactionID;}
    public int getUserID() {return userID;}
    public String getDate() {return date;}
    public String getTime() {return time;}
    public String getUsername() {return username;}
    public String getItem() {return item;}
    public float getTotal() {return total;}

    public void setTransactionID(int transactionID) {this.transactionID = transactionID;}
    public void setUserID(int userID) {this.userID = userID;}
    public void setDate(String date) {this.date = date;}
    public void setTime(String time) {this.time = time;}
    public void setUsername(String username) {this.username = username;}
    public void setTotal(float total) {this.total = total;}
    public void setItem(String item) {this.item = item;}

}