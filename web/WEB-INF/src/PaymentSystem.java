import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentSystem {
    private static final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static boolean doPayment(int userID, String price, String peanut, String userType, String appName, HttpSession session){
        boolean flag = false;
        try{
            Connection con = DatabaseConn.getConnection();
            Statement statement = con.createStatement();

            int peanutValue = Integer.parseInt(peanut)-Integer.parseInt(price);
            String query = "UPDATE users " + "SET peanut="+String.valueOf(peanutValue)+" WHERE id in ("+userID+")";
            statement.executeUpdate(query);
            session.setAttribute("amountPeanut",peanutValue);
            con.close();
            saveTransaction(price,true,String.valueOf(userID),userType, appName);
            flag = true;

        }catch (Exception e){

        }
        return flag;
    }

    public static boolean doRefund(int userID, String price, String peanut,String userType, String appName, HttpSession session){
        boolean flag = false;
        try{
            Connection con = DatabaseConn.getConnection();
            Statement statement = con.createStatement();

            int peanutValue = Integer.parseInt(peanut) + Integer.parseInt(price);
            String query = "UPDATE users " + "SET peanut="+String.valueOf(peanutValue)+" WHERE id in ("+userID+")";
            statement.executeUpdate(query);
            session.setAttribute("amountPeanut",peanutValue);
            con.close();
            saveTransaction(price,false,String.valueOf(userID),userType,appName);
            flag = true;
        }catch (Exception e){
        }
        return flag;
    }

    public static void saveTransaction(String peanut, boolean payment, String userID, String userType, String appName){
        try{
            Connection con = DatabaseConn.getConnection();
            Statement statement = con.createStatement();
            Date date = new Date();
            String query = "insert into peanuttransaction (userid, transaction_amount, payment, usertype, date_time, appname) " +
                    "Values ('" + Integer.parseInt(userID) + "','" + peanut+"'," + payment+ ", '" +
                    userType + "',  '" +sdf.format(date)+ "', '"+ appName+"');";
            System.out.println(query);
            statement.executeUpdate(query);
            con.close();
        }catch (Exception e){
        }
    }
}
