package com.scramblelovers.scrabble;

import java.util.ResourceBundle;

public class Msg {
    private static ResourceBundle localMessagesBundle = ResourceBundle.getBundle("Msg");

    public static String msg(String messageID) {
         return localMessagesBundle.getString(messageID);
    }
}
