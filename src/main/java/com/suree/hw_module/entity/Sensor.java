package com.suree.hw_module.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Sensor {
    private String clientIP;
    private String clientPORT;
    private String serverIP;
    private String serverPORT;
    private String bootloaderTIMER;
    private String version;
    private String tel;
    private String appText;
    private String logtime;
}
