package com.suree.hw_module.mina.singleton;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**  
 * @Description: 单例工具类，保存所有mina客户端连接  
 * @author whl  
 * @date 2014-9-29 上午10:09:15  
 *  
 */
@Component
public class SessionMap {  
      
    private final static Log log = LogFactory.getLog(SessionMap.class);
      
    private static Map<String, Map<String, IoSession>> sessionMap = null;

    private Map<String, SessionMap>Semap = new HashMap<>();

    private Map<String, IoSession>Iomap = new HashMap<>();
      
      
    //构造私有化 单例  
    private SessionMap(){}

    public void put(String key, Map<String, IoSession> map){
        this.sessionMap.put(key, map);
    }
    public Map<String, IoSession> get(String key){
        return this.sessionMap.get(key);
    }
      
      
    /**  
     * @Description: 获取唯一实例  
     * @author whl  
     * @date 2014-9-29 下午1:29:33  
     */  
    public static Map<String, Map<String, IoSession>> newInstance(){
        log.debug("SessionMap单例获取---");  
        if(sessionMap == null){  
            sessionMap = new HashMap();
        }  
        return sessionMap;  
    }  
      
      
    /**  
     * @Description: 保存session会话  
     * @author whl  
     * @date 2014-9-29 下午1:31:05  
     */  
//    public void addSession(String key, IoSession session){
//        log.debug("保存会话到SessionMap单例---key=" + key);
//        this.Iomap.put(key, session);
//    }

    /**
     * @Description: 根据key查找缓存的session
     * @author whl
     * @date 2014-9-29 下午1:31:55  
     */
    public IoSession getSession(String key){
        log.debug("获取会话从SessionMap单例---key=" + key);
        return this.Iomap.get(key);
    }


//    /**
//     * @Description: 根据key查找缓存的session
//     * @author whl
//     * @date 2014-9-29 下午1:31:55
//     */
//    public IoSession removeSession(String key){
//        log.debug("删除会话从SessionMap单例---key=" + key);
//        return this.Iomap.remove(key);
//    }

    /**  
     * @Description: 发送消息到客户端  
     * @author whl  
     * @date 2014-9-29 下午1:57:51  
     */  
//    public void sendMessage(String[] keys, Object message){
//        for(String key : keys){
//            IoSession session = getSession(key);
//
//            System.out.println("反向发送消息到客户端Session---key=" + key + "----------消息=" + message);
//            if(session == null){
//                System.out.println("session为null");
//                return;
//            }
//
//            //输入字符串5A A5 0A 15 03 06 00 10 00 00
//            //输出字符串0A 15 03 06 00 10 00 00 00 00
////            System.out.println("session不等于null 就把数据发送到客户端 " );
//            String[] cmds = (message.toString()).split(" ");
//            byte[] bytes = new byte[cmds.length];
//            int i=0;
//            for(String b : cmds){
//                if(b.equals("5A")||b.equals("A5")){
//                    continue;
//                }
//
//                if(b.equals("FF")){
//                    bytes[i++] = -1;
//                }else{
//                    bytes[i++] = Byte.parseByte(b, 16);
//                }
//            }
//
//            System.out.println("消息的长度是： " + i);
//            session.write(IoBuffer.wrap(bytes));
//        }
//
//
//    }
//
//
      
  
} 