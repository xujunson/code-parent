package com.atu.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static Date str2Date(String d){
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date date=null;
        try {
            date=df.parse(d);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }
    public static String date2Str(Date d){
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String ss="";
        try {
            ss=df.format(d);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ss;
    }

    public static String date2Str2(Date d){
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMdd");
        String ss="";
        try {
            ss=df.format(d);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ss;
    }

    public static String datetime2Str(Date d){
        SimpleDateFormat df=new SimpleDateFormat("yyyyMMddhhmmss");
        String ss="";
        try {
            ss=df.format(d);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ss;
    }
}
