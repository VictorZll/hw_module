package com.suree.hw_module.controller.baseController;

import com.suree.hw_module.constant.Constants;
import com.suree.hw_module.entity.Sensor;
import com.suree.hw_module.mina.DeviceMap;
import com.suree.hw_module.mina.singleton.PackageMap;
import com.suree.hw_module.mina.singleton.SessionMap;
import com.suree.hw_module.utils.APPHandles;
import com.suree.hw_module.utils.GPSFilesHandlerUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/sensor/handle")
public class SensorHandleController {

    private void sendApp( Object message,IoSession session){

            System.out.println("反向发送消息到客户端Session---" + "----------APP=" + message);
            if(session == null){
                System.out.println("session为null");
                return;
            }
            if(StringUtils.isEmpty( message)){
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

            if(session == null){
                System.out.println("session为null");
                return;
            }
            String[] cmds = (msg1.toString()).split(" ");
            byte[] bytess = new byte[cmds.length];
            int i=0;
            for(String b : cmds){

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

    private void handleOneData1(String tel,String msg){
        Map<String, Map<String, IoSession>> stringMapMap = SessionMap.newInstance();
            IoSession session=SessionMap.newInstance().get(tel).get(tel);
            StringBuilder msg1=new StringBuilder();
            byte[] bytes=msg.getBytes();
            for (int i=0;i<bytes.length;i++){
                byte b=bytes[i];
                Integer.toHexString(b);
                msg1.append(Integer.toHexString(b)+" ");
            }
            msg1.substring(0,msg1.length()-1);
            System.out.println( msg1.toString()+"msg1");

            if(session == null){
                System.out.println("session为null");
                return;
            }
            String[] cmds = (msg1.toString()).split(" ");
            byte[] bytess = new byte[cmds.length];
            int i=0;
            for(String b : cmds){
                if(b.equals("FF")){
                    bytess[i++] = -1;
                }else{
                    bytess[i++] = Byte.parseByte(b, 16);
                }
            }

            System.out.println("消息的长度是： " + i);
            session.write(IoBuffer.wrap(bytes));


    }

    @RequestMapping("/listForm")
    public List<Sensor> listForm(){
//        Sensor sensor=new Sensor();
//        sensor.setClientIP("192.168.123.1");
//        sensor.setClientPORT("57741");
//        sensor.setServerIP("192.168.5.123");
//        sensor.setServerPORT("28899");
//        sensor.setTel("12345678911");
//        sensor.setBootloaderTIMER("8s");
//        sensor.setVersion("v2.0.1");
        List<Sensor> list =new ArrayList<>();
        Map<String,Object> deviceMap = DeviceMap.newInstance();
        for (String k:deviceMap.keySet()){
            Sensor sensor=new Sensor();
            Map<String,String> map =(Map<String,String>) deviceMap.get(k);
            if(!StringUtils.isEmpty(map.get("time"))){
                sensor.setBootloaderTIMER(map.get("time"));
            }
            if(!StringUtils.isEmpty(map.get("ClientIP"))){
                sensor.setClientIP(map.get("ClientIP"));
            }
            if(!StringUtils.isEmpty(map.get("ClientPORT"))){
                sensor.setClientPORT(map.get("ClientPORT"));
            }
            if(!StringUtils.isEmpty(map.get("ServerIP"))){
                sensor.setServerIP(map.get("ServerIP"));
            }
            if(!StringUtils.isEmpty(map.get("ServerPORT"))){
                sensor.setServerPORT(map.get("ServerPORT"));
            }
            if(!StringUtils.isEmpty(map.get("Version"))){
                sensor.setVersion(map.get("Version"));
            }
            if(!StringUtils.isEmpty(map.get("appText"))){
                sensor.setAppText(map.get("appText"));
            }
            sensor.setTel(k);

            Collections.addAll(list,sensor);
        }
        return list;
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
        res.put("data",list);
        return res;

    }

    @RequestMapping("/handleStr")
    public Map<String,Object> handleStr(String str){
        String msg=str;
        handleData1(msg.trim());
        Map<String,Object> res=new HashMap<>();
        res.put("status",1);
        return res;
    }

    @RequestMapping("/ipHandle")
    public Map<String,Object> setIp(@RequestParam(required = false)String ip,@RequestParam(required = false)String tel, boolean set){
        String msg="";
        if(set){
            msg="IP"+ip;
        }else {
            msg="RIP";
        }
        if(StringUtils.isEmpty(tel)){
            handleData1(msg);
        }else {
            handleOneData1(tel,msg);
        }

        Map<String,Object> res=new HashMap<>();
        res.put("status",1);
        return res;
    }

    @RequestMapping("/portHandle")
    public Map<String,Object> setPort(@RequestParam(required = false)String port,@RequestParam(required = false)String tel, boolean set){
        String msg="";
        if(set){
             msg="PORT"+port;
        }else {
            msg="RPORT";
        }
        if(StringUtils.isEmpty(tel)){
            handleData1(msg);
        }else {
            handleOneData1(tel,msg);
        }
        Map<String,Object> res=new HashMap<>();
        res.put("status",1);
        return res;
    }

    @RequestMapping("/uploadAPPBin")
    public void uploadAPPBin(HttpServletRequest request){
        try {
            Part app = request.getPart("APP");
            List<String> list=APPHandles.readBinApp(app.getInputStream(),(int) app.getSize());
            System.out.println( app.getSize());
            System.out.println("list"+list);
            System.out.println("0"+list.get(0));
            for (int i=0;i<list.size();i++){
                PackageMap.newInstance().put(i,list.get(i));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @RequestMapping("/sendUPDATE")
    public Map<String,Object> sendUPDATE(String udt,@RequestParam(required = false) String tel){
        Map<String,Object> map=new HashMap<>();
        Map<String, Map<String, IoSession>> stringMapMap = SessionMap.newInstance();
        int size = stringMapMap.size();
        map.put("status",1);
        if(size<1){
            map.put("status",0);
        }else {
            if(StringUtils.isEmpty(tel)){
                handleData1(udt);
            }else {
                handleOneData1(tel,udt);
            }
        }
        return map;
    }

    @RequestMapping("/sendAppPack")
    public Map<String,Object> sendAppPack(String pack,@RequestParam(required = false) String tel){
        Map<String,Object> map=new HashMap<>();
        Map<String, Map<String, IoSession>> stringMapMap = SessionMap.newInstance();
        int size = PackageMap.newInstance().keySet().size();
        map.put("status",1);
        if(size<1){
            map.put("status",0);
        }else {
            String msg=(String) PackageMap.newInstance().get(Integer.parseInt(pack)-1);
            if(StringUtils.isEmpty(tel)){
                for (String k:stringMapMap.keySet()){
                    sendApp(msg,stringMapMap.get(k).get(k));
                }
            }else {
                sendApp(msg,stringMapMap.get(tel).get(tel));
            }

        }

        return map;
    }

    @RequestMapping("/countPackage")
    public Map<String,Object> countPackage(){
        Map<String,Object> map=new HashMap<>();
        map.put("count", PackageMap.newInstance().keySet().size());
        return map;
    }

}
