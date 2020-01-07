package com.suree.hw_module.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
        File f=new File("C:\\Users\\shinelon\\Desktop\\APP.bin");
        try {
            InputStream in=new FileInputStream(f);
            List<String> list=readBinApp(in);
            int i=0;
            list.forEach(l->{
                System.out.println(":"+l);
            });
//            System.out.println("--"+readBinApp(in).get(1).length()+":"+readBinApp(in).get(1));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static List<String> readBinApp(InputStream in){
        List<String> res=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        byte[] b=new byte[1024];
        int n=0;
        try {
            while (in.read(b)!=-1){
                for (int i=0;i<b.length;i++){
                    System.out.print(intToHex((b[i]& 0xff))+" ");
                    sb.append(intToHex((b[i]& 0xff))+" ");
                    n++;
                    if(n%10317==0){
                        res.add(sb.toString());
                        sb=new StringBuilder();
                        System.out.println("开始分包");
                    }
                }
            }
            res.add(sb.toString());
            System.out.println("分包完成");
        }catch (Exception e){
            e.printStackTrace();
        }

        return res;
    }
}
