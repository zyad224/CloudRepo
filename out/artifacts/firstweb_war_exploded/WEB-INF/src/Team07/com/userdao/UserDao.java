package Team07.com.userdao;

import DatabaseConnection.DatabaseConn;
import Team07.com.user.Transaction;
import Team07.com.user.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    public void insertUser(User user) {

        Connection conn = null;
        Statement sta = null;

        try {
            conn = DatabaseConn.getConnection();
            sta = conn.createStatement();
            String sql = "INSERT INTO usertable (user_name,user_password)VALUES('"
                    + user.getUserName()
                    + "','"
                    + user.getUserPassword()
                    + "')";
            sta.executeUpdate(sql);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public Boolean userIsExist(String userName){
        Connection conn = null;
        Statement sta = null;
        ResultSet rs=null;


        try {
            conn=DatabaseConn.getConnection();
            sta=conn.createStatement();
            String sql = "SELECT * FROM usertable WHERE user_name = '"
                    + userName + "'";
            rs =sta.executeQuery(sql);
            if(!rs.next()){
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public User judgementUserPassword(String userName,String userPassword){
        Connection conn = null;
        Statement sta=null;
        ResultSet rs=null;
        User user=null;

        try {
            conn=DatabaseConn.getConnection();
            sta=conn.createStatement();
            String sql = "SELECT * FROM usertable WHERE user_name = '"
                    + userName + "' AND user_password= '" + userPassword + "'";
            rs = sta.executeQuery(sql);
            while(rs.next()){
                user=new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUserName(rs.getString("user_name"));
                user.setUserPassword(rs.getString("user_password"));
                user.setUserPeanuts(rs.getInt("user_peanut"));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public User queryUserPeanut(String username){
        Connection conn = null;
        Statement sta=null;
        ResultSet rs=null;
        User user=null;

        try {
            conn=DatabaseConn.getConnection();
            sta=conn.createStatement();
            String sql = "SELECT * FROM usertable WHERE user_name = '" + username + "'";
            rs = sta.executeQuery(sql);
            while(rs.next()){
                user=new User();
                user.setUserId(rs.getInt("user_id"));
                user.setRole(rs.getString("user_role"));
                user.setUserName(rs.getString("user_name"));
                user.setUserPeanuts(rs.getInt("user_peanut"));
                System.out.println(user.getUserPeanuts());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public void increaseUserPeanut(User user,int number){
        Connection conn = null;
        Statement sta=null;

        try {
            conn=DatabaseConn.getConnection();
            sta=conn.createStatement();
            int newUserPeanut=user.getUserPeanuts()+number;
            String sql = "UPDATE usertable SET user_peanut='" +newUserPeanut + "' WHERE user_name = '" + user.getUserName() + "'";
            System.out.println(sql);
            sta.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateUserPeanut(User user,int number){

        Connection conn = null;
        Statement sta=null;

        try {
            conn=DatabaseConn.getConnection();
            sta=conn.createStatement();
            //int newUserPeanut=user.getUserPeanuts()+number;
            String sql = "UPDATE usertable SET user_peanut='" +number + "' WHERE user_name = '" + user.getUserName() + "'";
            System.out.println(sql);
            sta.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTransaction(String sender,String receiver,int peanuts,String title){
        Connection conn = null;
        Statement sta=null;


        try {
            conn=DatabaseConn.getConnection();
            sta=conn.createStatement();
            String sql = "INSERT INTO transactiontable (transaction_sender,transaction_peanuts,transaction_receiver,transaction_content)VALUES('"
                    +sender+"',"
                    +peanuts+",'"
                    +receiver+"','"
                    +title+"')";
            sta.executeUpdate(sql);
            System.out.println(sql);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Transaction> queryAllTransaction(String sender) {

        Connection conn = null;
        Statement sta=null;
        ResultSet rs=null;
        ArrayList<Transaction> list=new ArrayList<Transaction>();

        try {
            conn=DatabaseConn.getConnection();
            sta=conn.createStatement();
            String sql = "SELECT * FROM transactiontable WHERE transaction_sender = '"
                    + sender + "'";
            rs=sta.executeQuery(sql);

            while(rs.next()){
                Transaction transaction =new Transaction();

                transaction.setTransactionSender(rs.getString("transaction_sender"));
                transaction.setTransactionPeanuts(rs.getInt("transaction_peanuts"));
                transaction.setTransactionContent(rs.getString("transaction_content"));
                transaction.setTransactionId(rs.getInt("transaction_id"));
                transaction.setTransactionReceiver(rs.getString("transaction_receiver"));

                list.add(transaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
