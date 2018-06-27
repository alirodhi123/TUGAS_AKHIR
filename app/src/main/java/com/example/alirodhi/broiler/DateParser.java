package com.example.alirodhi.broiler;

/**
 * Created by alirodhi on 6/23/2018.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateParser {
    public static String parseDateToDayDateMonthYear(String date){
        SimpleDateFormat sourcePatternDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat targetPatternDate = new SimpleDateFormat("EEE, dd MMMM yyyy HH:mm");

        String targetDate;

        Date sourceDate = parseToDate(date);
        targetDate = targetPatternDate.format(sourceDate);
        return targetDate;
    }

    public static Date parseToDate(String date){
        SimpleDateFormat sourcePatternDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date sourceDate;
        try {
            sourceDate = sourcePatternDate.parse(date);
            sourceDate.setTime(sourceDate.getTime() + 25200000);
        } catch (ParseException e){
            e.printStackTrace();
            sourceDate = null;
        }
        return sourceDate;
    }

    public static String parseToDateClean(String date){
        SimpleDateFormat destinationTime = new SimpleDateFormat("HH:mm");
        SimpleDateFormat destinationDate = new SimpleDateFormat("dd/MM/yy");

        Date source = parseToDate(date);
        Date now = new Date();

        Calendar sourceCal = Calendar.getInstance();
        sourceCal.setTime(source);

        Calendar nowCal = Calendar.getInstance();
        sourceCal.setTime(now);

        if (sourceCal.get(Calendar.YEAR) == nowCal.get(Calendar.YEAR)
                && sourceCal.get(Calendar.MONTH) == nowCal.get(Calendar.MONTH)
                && sourceCal.get(Calendar.DAY_OF_YEAR) == nowCal.get(Calendar.DAY_OF_YEAR)){
            return destinationTime.format(source);
        } else {
            nowCal.add(Calendar.DATE, -1);
            if(sourceCal.get(Calendar.YEAR) == nowCal.get(Calendar.YEAR)
                    && sourceCal.get(Calendar.MONTH) == nowCal.get(Calendar.MONTH)
                    && sourceCal.get(Calendar.DAY_OF_YEAR) == nowCal.get(Calendar.DAY_OF_YEAR)){
                return "YESTERDAY";
            } else {
                return destinationDate.format(source);
            }
        }
    }
}

