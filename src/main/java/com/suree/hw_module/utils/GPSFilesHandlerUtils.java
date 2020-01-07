package com.suree.hw_module.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GPSFilesHandlerUtils {


    /**
     * 读文件的方法
     * @param filePath
     * @return
     */
    public static List<String> readFiles(String filePath){
        List<String> res=new ArrayList<>();
        File srcFile = new File(filePath);
        if(srcFile.exists()){
            try {
//                long timeStart = System.currentTimeMillis();
                FileReader fileReader = new FileReader(srcFile);
                fileReader.getEncoding();
                LineNumberReader reader = new LineNumberReader(fileReader);
                int number = getTextLines(filePath);//设置指定行数
//                System.out.println(number);
                String txt = "";
                int lines = 0;
                    while (txt != null) {
                        lines++;
                        txt = reader.readLine();
                        if (lines <= number) {
//                            System.out.println("第" + reader.getLineNumber() + "的内容是：" + txt );
//                            System.out.println(txt.indexOf("x="));
//                            System.out.println("->"+ txt.substring(txt.indexOf("x="),txt.indexOf("x=")+25));
                            res.add(txt);

//                            long timeEnd = System.currentTimeMillis();
//                            System.out.println("总共花费：" + (timeEnd - timeStart) + "ms");
//                            System.exit(0);
                        }
                    }

                reader.close();
                fileReader.close();
                BufferedReader buffered = new BufferedReader(reader);
                buffered.close();
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally {

            }
        }

        return res;
    }

    private static int getTextLines(String filePath) throws IOException {
        FileReader fr = new FileReader(filePath); //这里定义一个字符流的输入流的节点流，用于读取文件（一个字符一个字符的读取）
        BufferedReader br = new BufferedReader(fr); // 在定义好的流基础上套接一个处理流，用于更加效率的读取文件（一行一行的读取）
        int x = 0; // 用于统计行数，从0开始
        while(br.readLine() != null) { // readLine()方法是按行读的，返回值是这行的内容
            x++; // 每读一行，则变量x累加1
        }
        return x; //返回总的行数
    }


    /**
     * 没有设置编码格式
     * ****************************************************************************************读文件用这个
     * @param filePath
     * @param fileName
     * @param content
     */
    public static void writeFiles(String filePath, String fileName, Map<String,Object> content) {
        StringBuffer saveText=new StringBuffer();
        saveText.append(content.toString());
        //这里需要做一个优化，只存数值即可，节省内存
        saveText.append("\r\n");
        File f = new File(filePath);

        f.mkdirs();//先创建文件路径
        File srcFile=new File(filePath,fileName);//再创建文件对象
        if(!srcFile.exists()){
            try {
                srcFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        ByteArrayInputStream in = null;//直接数组输入流
        BufferedOutputStream out = null;//字符输出流
        try{
            in = getStringStream(saveText.toString());
            out=new BufferedOutputStream(new FileOutputStream(srcFile,true));
            byte[] b=new byte[1024*1024*11];//待优化：重写hashcode方法
            int num=0;
            while ((num=in.read(b))!=-1){
                out.write(b,0,num);
                out.flush();
            }
        }catch (FileNotFoundException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (IOException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if(in!=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if(out!=null) {
                try {
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * 输入流->字符串
     * @param is
     * @return
     */
    public static String readInputStream(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int length = 0;
            byte[] buffer = new byte[1024];
            while ((length = is.read(buffer)) != -1) {
                baos.write(buffer, 0, length);
            }
            is.close();
            baos.close();
            //或者用这种方法
            //byte[] result=baos.toByteArray();
            //return new String(result);
            return baos.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "获取失败";
        }
    }

    /**
     * 字符串->输入流
     * @param sInputString
     * @return
     */
    private static ByteArrayInputStream getStringStream(String sInputString) {
        if (sInputString != null && !sInputString.trim().equals("")) {
            try {
                ByteArrayInputStream tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
                return tInputStringStream;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }


    /**
     *******************************************************************************写文件用这个
     * @Title: writeFile
     * zll
     * @Description: 写文件
     * @param @param filePath 文件路径
     * @param @param fileContent    文件内容
     * @return void    返回类型
     * @throws
     */
    public static void writeFile(String filePath,String fileName, String fileContent) {
        try {
            File sf = new File(filePath);
            if(!sf.exists()){
                sf.mkdirs();
            }
            File f=new File(sf,fileName);
            if (!f.exists()) {
                f.createNewFile();
            }
            OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(f,true), "UTF-8");
            BufferedWriter writer = new BufferedWriter(write);
            writer.write(fileContent);
            writer.close();
            write.close();
        } catch (Exception e) {
            System.out.println("写文件内容操作出错");
            e.printStackTrace();
        }finally {

        }
    }



    /**
     * 读取文件内容
     *zll
     * @param filePath
     * String 如 c:\\1.txt 绝对路径
     * @return boolean
     */
    public static List<String> readFile(String filePath) {
        List<String> fileContent=new ArrayList<>();
        try {
            File f = new File(filePath);
            if (f.isFile() && f.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(f), "UTF-8");
                BufferedReader reader = new BufferedReader(read);
                String line;
                while ((line = reader.readLine()) != null) {
                    fileContent.add(line.substring(1,line.length()-1));//在写入过程中，写入了多余的{}，这里把它们去掉
                }
                read.close();
            }
        } catch (Exception e) {
            System.out.println("读取文件内容操作出错");
            e.printStackTrace();
        }
        return fileContent;
    }
//    public static void main(String[] args) {
////        System.out.println(readFile("C:\\GPS定位测试\\log哈哈哈.txt"));
////        writeFile("C:\\GPS定位测试哈哈哈","log哈哈哈.txt","哈哈哈\r\n");
////        writeFile("C:\\GPS定位测试哈哈哈","log哈哈哈.txt","呵呵呵\r\n");
////        System.out.println(readFile("C:\\GPS定位测试\\log.txt"));
//        List<String> list=readFile("C:\\GPS定位测试\\2019-11-22_0001_log.txt");
//        System.out.println(readStringToList(list));
//    }


    /**
     * 读取文件内容
     *zll
     * @param filePath
     * String 如 c:\\1.txt 绝对路径
     * @return boolean
     */
    public static List<String> readSensorFile(String filePath) {
        List<String> fileContent=new ArrayList<>();
        try {
            File f = new File(filePath);
            if (f.isFile() && f.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(f), "UTF-8");
                BufferedReader reader = new BufferedReader(read);
                String line;
                while ((line = reader.readLine()) != null) {
                    if(line.contains("\uFEFF")){  //记事本bug
                        line=line.substring(1,line.length());
                    }
                    fileContent.add(line);//在写入过程中，写入了多余的{}，这里把它们去掉
                }
                read.close();
            }
        } catch (Exception e) {
            System.out.println("读取文件内容操作出错");
            e.printStackTrace();
        }
        return fileContent;
    }

    public static void main(String[] args) {
//        System.out.println(readFile("C:\\GPS定位测试\\log哈哈哈.txt"));
//        writeFile("C:\\GPS定位测试哈哈哈","log哈哈哈.txt","哈哈哈\r\n");
//        writeFile("C:\\GPS定位测试哈哈哈","log哈哈哈.txt","呵呵呵\r\n");
//        System.out.println(readFile("C:\\GPS定位测试\\log.txt"));
        List<String> list=readSensorFile("C:\\GPS定位测试\\history\\2019\\12\\18\\0014_898604381518C1992113_log.txt");
        System.out.println(list);
       for (int i=0;i<list.size();i++){
           if(list.get(i).contains("\uFEFF")){
               String context=list.get(i);
               context=context.substring(1,context.length());
//               System.out.println(context);
               list.set(i,context);
           }
           System.out.println(list.get(i));
       }
    }

    public static List<Map<String,Object>> readStringToList(List<String> list){
        List<Map<String,Object>> res=new ArrayList<>();
        for (String str:list){
            String[] strs=str.split(",");
            Map<String,Object> map=new HashMap<>();//每一个对象用一个map集合存储
            for (String ss:strs){
                String[] sss=ss.split("=");
                map.put(sss[0].trim(),sss[1].trim());//去掉空格
            }
            res.add(map);
        }
        System.out.println("res----------------------------------------"+res);
        return res;
    }


    public static List<Map<String,Object>> readSensorValue(List<String> list){
        List<Map<String,Object>> res=new ArrayList<>();
        for (String str:list){
            String[] strs=str.split(",");
            Map<String,Object> map=new HashMap<>();//每一个对象用一个map集合存储
            for (String ss:strs){
                String[] sss=ss.split("=");
                map.put(sss[0].trim(),sss[1].trim());//去掉空格
            }
            res.add(map);
        }
        System.out.println("res----------------------------------------"+res);
        return res;
    }

}
