import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentSystem {
    private static final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private static int fivePeanuts=5;
    private static String microservice="microservice";

    public static boolean doPayment(int userID, String price, String peanut, String userType, String appName, HttpSession session){
        boolean flag = false;
        try{
            Connection con = DatabaseConn.getConnection();
            Statement statement = con.createStatement();

            // this is used if you want to deduct pricr from peanuts
            //int peanutValue = Integer.parseInt(peanut)-Integer.parseInt(price);
            int peanutValue = Integer.parseInt(peanut)-fivePeanuts;

            String query = "UPDATE users " + "SET peanut="+String.valueOf(peanutValue)+" WHERE id in ("+userID+")";
            statement.executeUpdate(query);
            session.setAttribute("amountPeanut",peanutValue);
            con.close();
            saveTransaction(String.valueOf(fivePeanuts),true,String.valueOf(userID),userType, appName);
            addPeanutsToAdmins(String.valueOf(fivePeanuts),appName);
            flag = true;

        }catch (Exception e){

        }
        return flag;
    }

    public static void addPeanutsToAdmins(String fivePeanuts,String appname){
        try {
            Connection con = DatabaseConn.getConnection();
            Statement statement = con.createStatement();
            String query = "SELECT peanut FROM admins where appname='" + appname +"';";
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            String peanutBeforeTransaction = rs.getString("peanut");
            int peanutAfterTransaction = Integer.parseInt(peanutBeforeTransaction)+(Integer.parseInt(fivePeanuts)-2);



            query = "UPDATE admins " + "SET peanut ="+String.valueOf(peanutAfterTransaction)+" WHERE appname='"+appname+"';";
            statement.executeUpdate(query);


            String query2 = "SELECT peanut FROM admins where usertype='" + microservice +"';";
            rs=statement.executeQuery(query2);
            rs.next();
            peanutBeforeTransaction = rs.getString("peanut");
            peanutAfterTransaction = Integer.parseInt(peanutBeforeTransaction)+1;
            query2 = "UPDATE admins " + "SET peanut ="+String.valueOf(peanutAfterTransaction)+" WHERE usertype='"+microservice+"';";
            statement.executeUpdate(query2);




        }catch (Exception e){

        }

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
