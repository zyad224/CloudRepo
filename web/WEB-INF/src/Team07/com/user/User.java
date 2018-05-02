package Team07.com.user;

public class User {
    private int userId;
    private String userName ;
    private String userPassword;
    private int userPeanuts;
    private String role;

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getUserPeanuts() {
        return userPeanuts;
    }
    public void setUserPeanuts(int userPeanuts) {
        this.userPeanuts = userPeanuts;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public String toString(){
        return "userId="+userId+",userName"+userName+",password="+userPassword+",userPeanuts="+userPeanuts;
    }

}
