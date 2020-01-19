package com.suree.hw_module.schedule;

import com.suree.hw_module.mina.DeviceMap;
import com.suree.hw_module.mina.singleton.SessionMap;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class TaskSchedule {





    @Async
    @Scheduled(cron = "*/60 * * * * ?")
    //每5s执行一次 */5 * * * * ?
    public void removeDevice(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String,Object> map = DeviceMap.newInstance();
        List<String> rmk=new ArrayList<>();
        if(map!=null&&map.size()>0){
            map.forEach((k,v)->{
                if(v!=null){
                    String logtime = ((Map<String, String>) v).get("logtime");
                    if(!StringUtils.isEmpty(logtime)){
                        try {
                            Date parse = sdf.parse(logtime);
                            Date date=new Date();
                            if(date.getTime()-parse.getTime()>50*1000){
                                System.out.println("执行了一次清空");
                                Collections.addAll(rmk,k);
                                SessionMap.newInstance().remove(k);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        if(rmk.size()>0){
            rmk.forEach(r->{
                map.remove(r);
            });
        }
    }
}

