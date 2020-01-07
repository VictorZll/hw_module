package com.suree.hw_module.mina;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储传感器发送的数据
 * Created by 旭瑞 on 2019/5/18.
 */
public class DeviceMap {
    private final static Log log = LogFactory.getLog(DeviceMap.class);
    private static Map deviceMap = null;

    //单例模式
    public static Map newInstance() {
        if (deviceMap == null) {
            deviceMap = new HashMap();
        }
        return deviceMap;
    }
    public void put(String key,Map<String,String> map){
        this.deviceMap.put(key, map);
    }
    public Map<String,String> get(String key){
        return (Map<String,String>)this.deviceMap.get(key);
    }
}
