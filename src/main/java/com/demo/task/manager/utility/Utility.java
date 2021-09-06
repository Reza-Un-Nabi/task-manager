package com.demo.task.manager.utility;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Utility {

    public static Date stringToDate(String date) {
        Date convertDate = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
            convertDate = simpleDateFormat.parse(date);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertDate;
    }
}
