package com.suree.hw_module.utils;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: hw_module
 * @description:
 * @author: zll
 * @create: 2020-01-07 23:17
 **/
public class APPHandles {

    /**
     * byte数组转hex
     * @param bytes
     * @return
     */
    public static String byteToHex(byte[] bytes){
        String strHex = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < bytes.length; n++) {
            strHex = Integer.toHexString(bytes[n] & 0xFF);
            sb.append((strHex.length() == 1) ? "0" + strHex : strHex); // 每个字节由两个字符表示，位数不够，高位补0
        }
        return sb.toString().trim();
    }
    /**
     * hex转byte数组
     * @param hex
     * @return
     */
    public static byte[] hexToByte(String hex){
        int m = 0, n = 0;
        int byteLen = hex.length() / 2; // 每两个字符描述一个字节
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[i] = Byte.valueOf((byte)intVal);
        }
        return ret;
    }

    private static String intToHex(int i){
        String hex="";
        hex=Integer.toHexString(i);
        if(hex.length()<2){
            return "0"+hex;
        }
        return hex;
    }

    public static void main(String[] args) {
        System.out.println(15424%1024);
        File f=new File("C:\\Users\\Administrator\\Desktop\\APP(3).bin");
        try {
            InputStream in=new FileInputStream(f);
            int size=(int) f.length();
//            System.out.println("size---"+size);
//            System.out.println("size---"+size/1024);
//            System.out.println("size---"+size%1024);
            List<String> list=readBinApp(in,size);
            int i=0;
            list.forEach(l->{
//                System.out.println(l);
//                System.out.println(":"+l.replaceAll(" ","").length());
            });
//            System.out.println("--"+readBinApp(in).get(1).length()+":"+readBinApp(in).get(1));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static List<String> readBinApp(InputStream in,int size){
        List<String> res=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        byte[] b=new byte[1024];
        int n=0;
        int len=0;
        int lastLine=size%1024;
        System.out.println("lastLine="+lastLine);
        try {
            while ((len=in.read(b,0,b.length))!=-1){
//                System.out.println();
//                System.out.println(n);
//                System.out.println("len--"+len);
//                if(n%10==0&&n!=0){
                if(n==15){
                    res.add(sb.toString());
                    sb=new StringBuilder();
                    System.out.println("开始分包");
                    for (int i=0;i<b.length;i++){
                        System.out.print(intToHex((b[i]& 0xff))+" ");
                        sb.append(intToHex((b[i]& 0xff))+" ");
                    }
                }else {
                    if(n==size/1024){
                        for (int i=0;i<b.length;i++){
                            if(i<lastLine){
                                System.out.print(intToHex((b[i]& 0xff))+" ");
                                sb.append(intToHex((b[i]& 0xff))+" ");
                            }
                        }
                    }else{
                        System.out.println("b.length--->"+b.length);
                        for (int i=0;i<b.length;i++){
                            System.out.print(intToHex((b[i]& 0xff))+" ");
                            sb.append(intToHex((b[i]& 0xff))+" ");
                        }
                    }
                }

                n++;

            }
            System.out.println("n--->"+n);
            res.add(sb.toString());
            System.out.println("分包完成");
            if(in!=null){
                in.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return res;
    }
}
