package com.khoders.pos.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {
    public static LocalDate parseDateToLocalDate(Date date)
    {
        try
        {
            date = new Date();
            Instant currentInstant = date.toInstant();
            ZonedDateTime zonedDateTime = currentInstant.atZone(ZoneId.systemDefault());
            LocalDate localDate  = zonedDateTime.toLocalDate();

            return localDate;

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDate(String str, String pattern)
    {
        try
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            dateFormat.setLenient(false);
            return dateFormat.parse(str);

        } catch (ParseException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public static String parseLocalDateString(LocalDate localDate, String pattern)
    {
        try
        {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);

            return localDate.format(dateFormat);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
    public static LocalDate parseLocalDate(String str, String pattern)
    {
        try
        {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(pattern);
            LocalDate localDate = LocalDate.parse(str, dateFormat);

            System.out.println("localDate: "+localDate);
            return localDate;

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static LocalDateTime parseDateTime(String str, String pattern)
    {
        try
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            return LocalDateTime.parse(str, formatter);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static java.sql.Date sqlDate()
    {
        long millis=System.currentTimeMillis();
        return new java.sql.Date(millis);
    }
}
