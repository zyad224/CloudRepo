import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static boolean checkUsername(String username){

        String patternString = "[a-z]{1,3}[0-9]{1,2}[a-z]{1,3}";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(username);
        boolean suitability = matcher.matches();
        System.out.println(suitability);
        return suitability;
    }
}
