package com.suree.hw_module.constant;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class Constants {

    //  public static final String BASE_PATH="C:\\";//测试

    public static final String BASE_PATH="C:\\tomcat\\apache-tomcat-10555\\apache-tomcat-8.5.47\\webapps";//图片存储远程

    public static final String host = "localhost";//测试
//    public static final String host = "192.168.8.109";//测试
//public static final String host = "118.31.245.183";//上线

    //    public static final int port = 10001;//测试
    public static final int port3Wheel = 10002;//测试

    public static final int serialNum=0;//excel表格实体类

    public static final int sensorNum=400;//传感器个数

    public static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //   public static final  String url = "http:" + "//" + "192.168.8.126:8080"; //本地

    public static final  String imgurl = "http:"+"//"+"118.31.245.183:10500";  //图片访问远程
//    public static final  String imgurl = "http:"+"//"+"192.168.8.109:8888";  //图片访问本地


    public static final String hwMembersUrl="C:\\环卫资料\\人员信息";

    public static final Boolean isOnline=true;
}
