package i10n;

import java.util.ResourceBundle;

public class Msg {
    private static ResourceBundle localMessagesBundle = ResourceBundle.getBundle("Msg");

    public static String msg(String messageID) {
         return localMessagesBundle.getString(messageID);
    }
    // pass it something like 
    // ("hiMessage", "Frank")
    // properties file contains "Hi, %s.  Nice to see you."
    // output will be "Hi, Frank.  Nice to see you." 
    public static String msg(String messageID, String in) {
        String s1 = msg(messageID);
        return String.format(s1, in);
    }
}
