package com.nttuyen.news.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * @author nttuyen266@gmail.com
 */
public class StringUtils {
    public static String convertToFriendlyURL(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String text = pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replace("đ", "d");
        text = text.replaceAll(" ", "-");

        //Special character
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if(ch < 45) continue;
            if(ch == 46 || ch == 47) continue;
            if(ch > 57 && ch < 65) continue;
            if(ch > 90 && ch < 95) continue;
            if(ch == 96) continue;
            if(ch > 122) continue;

            builder.append(ch);
        }
        text = builder.toString();

//        for (int i = 33; i < 48; i++){
//            text = text.replaceAll(String.valueOf((char) i), "");
//        }
//        for (int i = 58; i < 65; i++){
//            text = text.replaceAll(String.valueOf((char) i), "");
//        }
//        for (int i = 91; i < 97; i++){
//            text = text.replaceAll(String.valueOf((char) i), "");
//        }
//        for (int i = 123; i < 127; i++) {
//            text = text.replaceAll(String.valueOf((char) i), "");
//        }

        return text;
    }
}
