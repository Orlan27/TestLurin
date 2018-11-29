
package com.zed.lurin.commons.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Llince
 */
public class Validations {

    public static boolean isNumeric(String str) {
        if (str != null && !str.equals("")) {
            for (char c : str.toCharArray()) {
                if (!Character.isDigit(c)) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    public static String formatDate(String format, Date curDate) {
        String dateToStr = null;
        SimpleDateFormat simpleFormat = new SimpleDateFormat(format);
        dateToStr = simpleFormat.format(curDate);
        return dateToStr;
    }
}
