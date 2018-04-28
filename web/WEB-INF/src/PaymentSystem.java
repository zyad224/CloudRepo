import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.Statement;

public class PaymentSystem {
    public static boolean doPayment(int userID, String price, String peanut, HttpSession session){
        boolean flag = false;
        try{
            Connection con = DatabaseConn.getConnection();
            Statement statement = con.createStatement();

            int peanutValue = Integer.parseInt(peanut)-Integer.parseInt(price);
            String query = "UPDATE users " + "SET peanut="+String.valueOf(peanutValue)+" WHERE id in ("+userID+")";
            statement.executeUpdate(query);
            session.setAttribute("amountPeanut",peanutValue);
            con.close();
            flag = true;

        }catch (Exception e){

        }
        return flag;
    }

    public static boolean doRefund(int userID, String price, String peanut,HttpSession session){
        boolean flag = false;
        try{
            Connection con = DatabaseConn.getConnection();
            Statement statement = con.createStatement();

            int peanutValue = Integer.parseInt(peanut) + Integer.parseInt(price);
            String query = "UPDATE users " + "SET peanut="+String.valueOf(peanutValue)+" WHERE id in ("+userID+")";
            statement.executeUpdate(query);
            session.setAttribute("amountPeanut",peanutValue);
            con.close();
            flag = true;
        }catch (Exception e){
        }
        return flag;
    }
}
