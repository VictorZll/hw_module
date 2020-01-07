package com.suree.hw_module.mina.singleton;

import com.suree.hw_module.mina.DeviceMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: hw_module
 * @description:
 * @author: zll
 * @create: 2020-01-08 00:21
 **/
public class PackageMap {
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
