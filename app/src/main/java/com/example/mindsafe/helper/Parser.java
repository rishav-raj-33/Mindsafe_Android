package com.example.mindsafe.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser {


   public static LocalDateTime dateTimeParser(String dateTime){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            return LocalDateTime.parse(dateTime, formatter);
        }
        return null;
    }
}
