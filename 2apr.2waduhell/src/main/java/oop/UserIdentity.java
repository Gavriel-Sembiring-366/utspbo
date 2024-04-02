package oop;

public class UserIdentity {
    private String userName;
    private int userId;
    private String role;
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public UserIdentity(){}

    public UserIdentity(String userName, int userId, String role){
        this.userName = userName;
        this.userId = userId;
        this.role = role;
    }
}
