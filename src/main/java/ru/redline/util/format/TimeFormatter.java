package ru.redline.util.format;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatter {

    private static final Long[] VALUES = {2419200L, 604800L, 86400L, 3600L, 60L, 1L};
    private static final String[] FORMATS = {" %s мес.", " %s нед.", " %s дн.", " %s час.", " %s мин.", " %s сек."  };

    public static String formatText(long time) {
        String result = "";
        time /= 1000L;
        long temp;
        for (int i = 0; i < VALUES.length; i++) {
            if ((temp = time / VALUES[i]) >= 1L) {
                result += String.format(FORMATS[i], temp);
                time -= temp * 2419200L;
            }
        }
        return result.trim();
    }

    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");

    public static String formatDateTime(long time) {
        return DATE_TIME_FORMAT.format(new Date(time));
    }
}
