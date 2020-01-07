package com.suree.hw_module.controller.baseController;

import com.suree.hw_module.constant.Constants;
import com.suree.hw_module.mina.DeviceMap;
import com.suree.hw_module.mina.singleton.SessionMap;
import com.suree.hw_module.utils.GPSFilesHandlerUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/sensor/handle")
public class SensorHandleController {
    public void sendApp( Object message,IoSession session){

            System.out.println("反向发送消息到客户端Session---" + "----------APP=" + message);
            if(session == null){
                System.out.println("session为null");
                return;
            }
            if(StringUtils.isEmpty((String) message)){
                return;
            }
//            System.out.println("session不等于null 就把数据发送到客户端 " );
            String[] cmds = (message.toString()).split(" ");
            byte[] bytes = new byte[cmds.length];
            int i=0;
            for (int j=0;j<cmds.length;j++){
                if(StringUtils.isEmpty(cmds[j])){
                    continue;
                }
                int num=Integer.parseInt(cmds[j],16);
                i++;
               bytes[j] = (byte)Integer.parseInt(Integer.toHexString(num),16);
            }

            System.out.println("消息的长度是： " + i);
            session.write(IoBuffer.wrap(bytes));

    }
    public void sendMessage(String[] keys, Object message,IoSession session){
        for(String key : keys){

            System.out.println("反向发送消息到客户端Session---key=" + key + "----------消息=" + message);
            if(session == null){
                System.out.println("session为null");
                return;
            }

            //输入字符串5A A5 0A 15 03 06 00 10 00 00
            //输出字符串0A 15 03 06 00 10 00 00 00 00
//            System.out.println("session不等于null 就把数据发送到客户端 " );
            String[] cmds = (message.toString()).split(" ");
            byte[] bytes = new byte[cmds.length];
            int i=0;
            for(String b : cmds){
                if(b.equals("5A")||b.equals("A5")){
                    continue;
                }

                if(b.equals("FF")){
                    bytes[i++] = -1;
                }else{
                    bytes[i++] = Byte.parseByte(b, 16);
                }
            }

            System.out.println("消息的长度是： " + i);
            session.write(IoBuffer.wrap(bytes));
        }


    }
    private void handleData(String msg){
        String[] strs=msg.split(",");
        String[] keys={strs[0]};
        String phone=strs[1];
        String msgsend=strs[0]+","+strs[2];
        if(strs[3]!=null){
            msgsend+=","+strs[3];
        }
        if(SessionMap.newInstance().get(phone)!=null){
            IoSession session=SessionMap.newInstance().get(phone).get(phone);
            StringBuilder msg1=new StringBuilder();
            byte[] bytes=msgsend.getBytes();
            for (int i=0;i<bytes.length;i++){
                byte b=bytes[i];
                Integer.toHexString(b);
                msg1.append(Integer.toHexString(b)+" ");
            }
            msg1.substring(0,msg1.length()-1);
            System.out.println( msg1.toString()+"msg1");
            Map<String,Object> map=new HashMap<>();
            map.put(new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()),msg1);
            GPSFilesHandlerUtils.writeFiles("C;/","send.txt",map);
//        sessionMap.sendMessage(keys,msg1);
            sendMessage(keys,msg1,session);
        }

    }
    private void handleData1(String msg){
        Map<String, Map<String, IoSession>> stringMapMap = SessionMap.newInstance();
        for (String k:stringMapMap.keySet()){
            IoSession session=SessionMap.newInstance().get(k).get(k);
            StringBuilder msg1=new StringBuilder();
            byte[] bytes=msg.getBytes();
            for (int i=0;i<bytes.length;i++){
                byte b=bytes[i];
                Integer.toHexString(b);
                msg1.append(Integer.toHexString(b)+" ");
            }
            msg1.substring(0,msg1.length()-1);
            System.out.println( msg1.toString()+"msg1");
//        sessionMap.sendMessage(keys,msg1);

            if(session == null){
                System.out.println("session为null");
                return;
            }

            //输入字符串5A A5 0A 15 03 06 00 10 00 00
            //输出字符串0A 15 03 06 00 10 00 00 00 00
//            System.out.println("session不等于null 就把数据发送到客户端 " );
            String[] cmds = (msg1.toString()).split(" ");
            byte[] bytess = new byte[cmds.length];
            int i=0;
            for(String b : cmds){
                if(b.equals("5A")||b.equals("A5")){
                    continue;
                }

                if(b.equals("FF")){
                    bytess[i++] = -1;
                }else{
                    bytess[i++] = Byte.parseByte(b, 16);
                }
            }

            System.out.println("消息的长度是： " + i);
            session.write(IoBuffer.wrap(bytes));
        }

        }


    @RequestMapping("/listSensor")
    public Map<String,Object> listSensor(){
        Map<String,Object> res=new HashMap<>();
        Map<String,Object> data=new HashMap<>();
        List<String> list=new ArrayList<>();
        Map<String, Map<String, IoSession>> sessionMap = (Map<String, Map<String, IoSession>>)SessionMap.newInstance();
        if(sessionMap.size()>=1){
            for (String k:sessionMap.keySet()){
                Map<String, IoSession> map=sessionMap.get(k);
                IoSession session = map.get(k);
                Map deviceMap = DeviceMap.newInstance();
                if(Constants.isOnline){
                    if(session!=null&&session.getRemoteAddress()!=null){
                        String ip="";
                        int port=0;
                        if(session.getRemoteAddress()!=null){
                            ip =((InetSocketAddress) session.getRemoteAddress()).getAddress().toString();
                            port =((InetSocketAddress) session.getRemoteAddress()).getPort();
                        }

                        if(deviceMap.get(k)!=null){
                            list.add(k+":设备已上线,已发送数据 -------------->ip="+ip+",port="+port+"--------------->"+(DeviceMap.newInstance().get(k)).toString());
                            data.put(k,k+":设备已上线,已发送数据 -------------->ip="+ip+",port="+port+"--------------->"+(DeviceMap.newInstance().get(k)).toString());
                        }else {
                            data.put(k,k+":设备已上线,未发送数据 -------------->ip="+ip+",port="+port);
                        }
                        data.put(k,map);
                    }
                }
            }
        }
//        for (int i=1;i<2500;i++){
////            Map<String,Object> map=new HashMap<>();
//            String code=DataHandleUtils.addNZero(i + "", 4);
//            IoSession session = sessionMap.getSession(code);
//            Map deviceMap = DeviceMap.newInstance();
//            if(Constants.isOnline){
//                if(session!=null){
//                    String ip =((InetSocketAddress) session.getRemoteAddress()).getAddress().toString();
//                    int port =((InetSocketAddress) session.getRemoteAddress()).getPort();
//                    if(deviceMap.get(code)!=null){
//                        list.add(code+":设备已上线,已发送数据 -------------->ip="+ip+",port="+port+"--------------->"+((Map<String,Object>)DeviceMap.newInstance().get(code)).toString());
//                        data.put(code,code+":设备已上线,已发送数据 -------------->ip="+ip+",port="+port+"--------------->"+((Map<String,Object>)DeviceMap.newInstance().get(code)).toString());
//                    }else {
//                        data.put(code,code+":设备已上线,未发送数据 -------------->ip="+ip+",port="+port);
//                    }
//                    data.put(code,map);
//                }
//            }
//
//        }
        res.put("data",list);
        return res;

    }

    @RequestMapping("/setAddr")
    public Map<String,Object> setAddr(String code,String addr,String tel){
        String msg=code+","+tel+",02,"+addr;
        handleData(msg);
        Map<String,Object> res=new HashMap<>();
        res.put("status","1");
        return res;
    }

    @RequestMapping("/setIpAndPort")
    public Map<String,Object> setIpAndPort(String code,String ip,String tel,String port){
//        String msg=code+","+tel+",03,"+ip+","+port;
        String msg="IP"+ip;
        handleData1(msg);
        msg="PORT"+port;
        handleData1(msg);
        Map<String,Object> res=new HashMap<>();
        res.put("status","1");
        return res;
    }

    @RequestMapping("/setIp")
    public Map<String,Object> setIp(String ip){
        String msg="IP"+ip;
        handleData1(msg);
        Map<String,Object> res=new HashMap<>();
        res.put("status","1");
        return res;
    }

    @RequestMapping("/setPort")
    public Map<String,Object> setPort(String port){
        String msg="PORT"+port;
        handleData1(msg);
        Map<String,Object> res=new HashMap<>();
        res.put("status","1");
        return res;
    }


//    @RequestMapping("/reset")
//    public Map<String,Object> reset(String code, String tel, HttpServletRequest request){
//        Map<String,Object> res=new HashMap<>();
//
//        try{
//            List<String> s = ReadBin.sendAppBin();
//            int slen=s.size();
//            System.out.println("slen-->"+slen);
//            String pack1=s.get(0);
//            String pack2=s.get(1);
//            String pack3=s.get(2);
//            Map<String, Map<String, IoSession>> ioMap = SessionMap.newInstance();
//            for(String k:ioMap.keySet()){
//                Map<String, IoSession> iMap = ioMap.get(k);
//                if(!iMap.isEmpty()){
//                    for (int i=0;i<3;i++){
//
//                        if(i==0){
//                            sendApp(pack1,iMap.get(k));
//                        }else if (i==1){
//                            sendApp(pack2,iMap.get(k));
//                        }else {
//                            sendApp(pack3,iMap.get(k));
//                        }
//                        Thread.currentThread().sleep(5000);
//                    }
//
//                }
//            }
////            handleData(s);
//
//            res.put("status","1");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return res;
//    }
}
