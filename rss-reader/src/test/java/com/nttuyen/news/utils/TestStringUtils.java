package com.nttuyen.news.utils;

import com.nttuyen.news.util.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author nttuyen266@gmail.com
 */
public class TestStringUtils {
    @Test
    public void testConvertToFriendlyURL() {
        String origin = "a á à ạ ả â ấ ầ ậ ẩ b c d đ e é è ẹ ẻ ê ế ề ệ ể f g h i í ì ị ỉ j k l m n o ó ò ọ ỏ ô ố ồ ộ ổ p q r s t u ú ù ụ ủ ư ứ ừ ự ử v w x y z";
        origin       += "A Á À Ạ Ả Â Ấ Ầ Ậ Ẩ B C D Đ E É È Ẹ Ẻ Ê Ế Ề Ệ Ể F G H I Í Ì Ị Ỉ J K L M N O Ó Ò Ọ Ỏ Ô Ố Ồ Ộ Ổ P Q R S T U Ú Ù Ụ Ủ Ư Ứ Ừ Ự Ử V W X Y Z";
        origin       += "1234567890";
        origin       += "`~!@#$%^&*()-_+|{}\t'\",./?<>";
        String converted = "a-a-a-a-a-a-a-a-a-a-b-c-d-d-e-e-e-e-e-e-e-e-e-e-f-g-h-i-i-i-i-i-j-k-l-m-n-o-o-o-o-o-o-o-o-o-o-p-q-r-s-t-u-u-u-u-u-u-u-u-u-u-v-w-x-y-z";
        converted       += "A-A-A-A-A-A-A-A-A-A-B-C-D-D-E-E-E-E-E-E-E-E-E-E-F-G-H-I-I-I-I-I-J-K-L-M-N-O-O-O-O-O-O-O-O-O-O-P-Q-R-S-T-U-U-U-U-U-U-U-U-U-U-V-W-X-Y-Z";
        converted       += "1234567890";
        converted       += "-_";

        System.out.println("Expected: " + converted);

        String after = StringUtils.convertToFriendlyURL(origin);
        System.out.println("After: " + after);
        Assert.assertEquals(converted, after);
    }
}
