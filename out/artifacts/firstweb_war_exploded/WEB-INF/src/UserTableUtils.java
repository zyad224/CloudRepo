import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserTableUtils {

    public static List<String> getUserInfo(String username, String pass){
        List<String> data = new ArrayList<String>();
        try{
            Connection conn = DatabaseConn.getConnection();
            Statement st = conn.createStatement();
            String userID = "select id, firstname, lastname from users where username='"+ username + "' AND password='" + pass +"';";
            ResultSet rs = st.executeQuery(userID);
            rs.next();
            int id = rs.getInt("id");
            String fname = rs.getString("firstname");
            String lname = rs.getString("lastname");
            //0
            data.add(String.valueOf(id));
            //1
            data.add(fname);
            //2
            data.add(lname);
            conn.close();
        }catch (Exception e){
        }
        return data;
    }
}
