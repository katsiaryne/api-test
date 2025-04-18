package in.res.helper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public abstract class DateFormatHelper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static String getCurrentDateTime() {
        return LocalDateTime.now(ZoneOffset.UTC).format(formatter);
    }

    public static String formatRequestToDateTime(String dateTime) {
        return dateTime.substring(0, 16);
    }
}
