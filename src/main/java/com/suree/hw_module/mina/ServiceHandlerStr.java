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
//        System.out.println("------------------------->"+msg);
        try {
            System.out.println("msg1--"+msg1);
            System.out.println(msg1.contains(":"));

            if(msg1.contains(":")){
                String[] strArr3= msg1.split(":");
                String phone=strArr3[0];
                Map<String,IoSession> map=new HashMap<>();
                maps.put("tel",strArr3[0]);
                maps.put("time",strArr3[1]);
                DeviceMap.newInstance().put(phone , maps);//根据phone卡号判断
                System.out.println(DeviceMap.newInstance().get(phone)+"llll");
                map.put(phone,session);
                if(SessionMap.newInstance().get(phone)!=null){
                    SessionMap.newInstance().remove(phone);
                }
                SessionMap.newInstance().put(phone , map);//把接收数据的卡号作为key，并新建一个session
            }




                msg1+=",0,0,0,0,0";//解决协议改变，包长不够的问题   ------》不管是什么协议，补充10位，防止出现空指针报错



            //存储传感器数据
            Calendar calendar=Calendar.getInstance();
            String year = calendar.get(Calendar.YEAR)+"";
            String month = DataHandleUtils.addNZero(calendar.get(Calendar.MONTH)+1+"",2) ;
            String day = DataHandleUtils.addNZero(calendar.get(Calendar.DAY_OF_MONTH)+"",2);
            Map<String,Object> mapss=new HashMap<>();
            mapss.put(Constants.sdf.format(new Date()),msg1);//保留原始数据
            if(msg1.split(",")[0]!=null&&msg1.split(",")[0].length()==4&&msg1.split(",")[1]!=null&&msg1.split(",")[1].length()==20){
                GPSFilesHandlerUtils.writeFiles("C:\\GPS定位文件\\historySensor\\"+year+"\\"+month+"\\"+day+"\\", msg1.split(",")[0]+"_"+msg1.split(",")[1]+"_log.txt", mapss);
            }


//            //将接收到的消息放到map集合
            String[] strArr2 = msg1.split(",");

            maps = deCodeBootLoader(strArr2, msg1, clientIP, clientPort);

            if(maps!=null){  //去除空指针
                log.info("maps---"+maps);
                String phone=maps.get("phone").toString();
                DeviceMap.newInstance().put(phone , maps);//根据phone卡号判断
//            if(SessionMap.newInstance().get(phone)==null){
////                Map<String,IoSession> map=new HashMap<>();
////                map.put(phone,session);
////                SessionMap.newInstance().put(phone,map);
////            }
                Map<String,IoSession> map=new HashMap<>();
                map.put(phone,session);
                if(SessionMap.newInstance().get(phone)!=null){
                    SessionMap.newInstance().remove(phone);
                }
                SessionMap.newInstance().put(phone , map);//把接收数据的卡号作为key，并新建一个session
//            if(SessionMap.newInstance().get(phone).get(phone )!=null){//删除单利中重复的数据
//                SessionMap.newInstance().get(phone).remove(phone);
//                SessionMap.newInstance().get(phone).put(phone  , session);
//            }
//                log.info(msg1+"----正常解析--停滞时间"+maps.get("stoptime"));
            }

        } catch (Exception e) {
 //           System.out.println(msg1+"数据不合理，不解析");//包长报错，坐标报错---直接跳过
//            if(!Constants.isOnline){
              e.printStackTrace();
//           }
  //         System.out.println(e.toString());
//            throw e;
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
