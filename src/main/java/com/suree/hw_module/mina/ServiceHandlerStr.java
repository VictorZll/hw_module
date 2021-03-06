package com.suree.hw_module.mina;
import com.suree.hw_module.constant.Constants;
import com.suree.hw_module.mina.singleton.SessionMap;
import com.suree.hw_module.utils.DataHandleUtils;
import com.suree.hw_module.utils.GPSFilesHandlerUtils;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.net.InetSocketAddress;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 直接存卡号作为key，遍历map集合
 * 百度坐标：接收到数据后，不解析，直接存到文件
 * @Atuoth ysl
 * Date 2019/9/19
 */
@Component(value = "ServiceHandlerStr")
@Slf4j
@Data
public class ServiceHandlerStr extends IoHandlerAdapter {


    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    private DecimalFormat df = new DecimalFormat("#0.0");
    private boolean isFirst = true;

    String name;

    Map maps;

    int num;

    int count1=0;//计数器

//    @Resource
//    PedicabDetailImp pedicabImp;
//
//    @Resource
//    MotorDetailImp motorImp;
//
//    @Resource
//    HrKaoqinImp kaoQin;


    public ServiceHandlerStr() {
        // TODO Auto-generated constructor stub
    }


    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
            throws Exception {
        log.error(cause.getMessage());
    }

    private String toDec(String hex) {
        int dec = Integer.parseInt(hex, 16);
        return "" + dec;
    }


    @Override
    public void messageReceived(IoSession session, Object message) {

        //客户端ip和端口号
        String clientIP = ((InetSocketAddress) session.getRemoteAddress()).getAddress().toString();
        String clientPort = ((InetSocketAddress) session.getRemoteAddress()).getPort() + "";
//        System.out.println("接收到服务端ip:" + clientIP);
        String msg = (String) message;//原数据串
        String msg1 = "";//转10进制后的字符串
        String[] strArr1 = msg.split(" ");
        for (int i = 0; i < strArr1.length; i++) {     //--------------包长是9
            String str = strArr1[i];
            int istr = Integer.parseInt(str, 16);
            byte[] b = {(byte) istr};
//                System.out.println(bytes+"bytes-------------------");
//                System.out.println(new String(b)+"-------------------->str");
            msg1 = msg1 + new String(b);
        }
        try {
            System.out.println("msg1--"+msg1);
            GPSFilesHandlerUtils.writeFile("C:\\GPS定位文件\\history\\", "new_log.txt", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"-->"+msg1+"\r\n");
            String appText="";
            String tel1="";
            String ServerIP="";
            String Version="";
            String ServerPORT="";
            String time="";

            //APP协议包
            if(msg1.contains(",")){
                 tel1=msg1.split(",")[0];
                if(!StringUtils.isEmpty(tel1)&&tel1.length()==20){
                    appText=msg1;
                }
            }
            //boot loader协议包
            if(msg1.contains(":")){
                String phone=msg1.split("\\:")[0];
                String tel=msg1.split("\\:")[1];
                if(!StringUtils.isEmpty(phone)&&phone.length()==20){
                    tel1=phone;
                    if(tel.contains(".")&&tel.split("\\.").length==4){
                        ServerIP=tel;
                    }
                    if(tel.contains(".")&&tel.split("\\.").length==3){
                        Version=tel;
                    }
                    if(tel.length()<=5&&tel.length()>=4){
                        ServerPORT=tel;
                    }
                    if(tel.length()<=2){
                        time=tel;
                    }
                }
            }

            //创建单例
            if(!StringUtils.isEmpty(tel1)&&tel1.length()==20){
                Map<String,IoSession> map=new HashMap<>();
                Map<String,Object> mapss=new HashMap<>();
                mapss.put("ClientIP",clientIP);
                mapss.put("ClientPORT",clientPort);
                mapss.put("time",time);
                mapss.put("ServerPORT",ServerPORT);
                mapss.put("Version",Version);
                mapss.put("ServerIP",ServerIP);
                mapss.put("tel",tel1);
                mapss.put("appText",appText);
                mapss.put("logtime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                DeviceMap.newInstance().put(tel1 , mapss);//根据phone卡号判断
                map.put(tel1,session);
                SessionMap.newInstance().put(tel1 , map);//把接收数据的卡号作为key，并新建一个session
            }
        } catch (Exception e) {
 //           System.out.println(msg1+"数据不合理，不解析");//包长报错，坐标报错---直接跳过
              e.printStackTrace();
        }

//        System.out.println("messageReceived 执行完毕" + clientIP);

    }


    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        // ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();

        log.debug("------------服务端发消息到客户端---");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        log.debug("远程session关闭了一个..." + session.getRemoteAddress().toString());
//        System.out.println("远程session关闭了一个..." + session.getRemoteAddress().toString());
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
//        System.out.println(session+"session--------------------------------------------------sessionCreated");

        log.debug(session.getRemoteAddress().toString() + "----------------------create");
//        System.out.println(session.getRemoteAddress().toString() + "----------------------create");

    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
            throws Exception {
        log.debug(session.getServiceAddress() + "IDS");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        log.debug("---连接打开：" + session.getLocalAddress());
    }

    private static byte[] getCMDBytes(String cmd) {
        String[] cmds = cmd.split(" ");
        byte[] bytes = new byte[cmds.length];
        int i = 0;
        for (String b : cmds) {
            if (b.equals("FF")) {
                bytes[i++] = -1;
            } else {
                bytes[i++] = Byte.parseByte(b, 16);
            }
        }
        return bytes;
    }

    //每一个线程
   private static final ThreadLocal<SimpleDateFormat> threadLocal = new  ThreadLocal<SimpleDateFormat>();

    private Map<String, String> deCodeBootLoader(String[] strArr, String msg, String clientIP, String clientPort) throws Exception {
        Map<String,String> resqMap=new HashMap<>();
        String tel=strArr[0];
        if(SessionMap.newInstance().get(tel)!=null){
            SessionMap.newInstance().remove(tel);

        }
        return resqMap;
    }



}
