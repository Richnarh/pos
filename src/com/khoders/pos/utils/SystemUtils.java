package com.khoders.pos.utils;

import java.security.MessageDigest;
import java.security.Security;
import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

public class SystemUtils {
    public static String genId()
    {
        try
        {
            String id = UUID.randomUUID().toString().replaceAll("-", "");

            try
            {
                boolean uuidStringMatcher = id.matches(".*[a-zA-Z]+.*");

                if (!uuidStringMatcher)
                {
                    Random random = new Random();
                    char cha = (char) (random.nextInt(26) + 'a');
                    int numToReplace = random.nextInt(9);

                    id = id.replaceAll(String.valueOf(numToReplace), String.valueOf(cha));

                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }

            return id;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String generateCode()
    {
        return DateUtils.parseLocalDateString(LocalDate.now(), "ddMMyy")
                + UUID.randomUUID().toString().substring(0, 7).toUpperCase();
    }

    public static String generateShortCode()
    {
        return UUID.randomUUID().toString().substring(0,4).toUpperCase();
    }

    public static String generateId()
    {

        return DateUtils.parseLocalDateString(LocalDate.now(), "ddMM")
                + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }

    public static String generateRefNo()
    {
        String uuid = UUID.randomUUID().toString().substring(0, 5).toUpperCase();
        String uuidDate = DateUtils.parseLocalDateString(LocalDate.now(), "ddMMyy");

        return uuidDate +"/"+uuid;
    }

    public static String hashText(String hashString)
    {
        try
        {
            return MessageDigest.getInstance("SHA-512").toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
