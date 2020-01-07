package com.suree.hw_module.utils;


import org.springframework.util.StringUtils;

public  class DataHandleUtils {

    public static String addNZero(String i,int num){
        if(i.length()<num){
             i="0"+i;
            return addNZero(i,num);
        }
        return i;
    }
    public static void main(String[] args){
        System.out.println( addNZero("1",4));
        System.out.println(SecondFormat((long) 36000));

    }

    public static String strToHexStr(String str){
        if(StringUtils.isEmpty(str)){
            return "";
        }
        byte[] bytes = str.getBytes();
        String res="";
        for (byte b:bytes){
//            System.out.println(b);
//            System.out.println(Integer.toHexString(b));
            res+=Integer.toHexString(b)+" ";
        }
//        System.out.println( res);
        return res.substring(0,res.length()-1);
    }

    public static String SecondFormat(long time){
        String res="";
        long second=0;
        long hour=0;
        long min=0;
        int day=0;
        try {
            if (time < 60) {
                second = Integer.parseInt(time + "");
                res=addNZero(second + "", 2) + "秒";
//                mainFreeze.setStaytime();
            }
            if (time >= 60 && time < 3600) {
                min = Integer.parseInt((Math.round(Math.floor((time - hour * 3600) / 60.0)) + "") + "");
                second = Integer.parseInt(((time - min * 60) + ""));
                res=addNZero(min + "", 2) + "分" + addNZero(second + "", 2) + "秒";
//                mainFreeze.setStaytime();
            }
            if (time >= 3600) {
                hour = Integer.parseInt((Math.round(Math.floor(time / 3600.0)) + "") + "");
                min = Integer.parseInt((Math.round(Math.floor((time - hour * 3600) / 60.0)) + "") + "");
                second = Integer.parseInt((time - min * 60 - hour * 3600) + "");
                res=addNZero(hour + "", 2) + "时" + addNZero(min + "", 2) + "分" + addNZero(second + "", 2) + "秒";
//                mainFreeze.setStaytime();
            }
            if (time >= 3600*24) {
                day = Integer.parseInt((Math.round(Math.floor(time / (3600.0*24))) + ""));
                hour = Integer.parseInt((Math.round(Math.floor((time-3600.0*24*day )/ 3600.0)) + "") + "");
                min = Integer.parseInt((Math.round(Math.floor((time -3600.0*24*day - hour * 3600) / 60.0)) + "") + "");
                second = Integer.parseInt((time -3600.0*24*day - min * 60 - hour * 3600) + "");
                res=addNZero(day+"天"+hour + "", 2) + "时" + addNZero(min + "", 2) + "分" + addNZero(second + "", 2) + "秒";
//                mainFreeze.setStaytime();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
}
