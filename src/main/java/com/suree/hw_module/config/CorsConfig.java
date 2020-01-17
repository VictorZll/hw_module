package com.suree.hw_module.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @program: hw
 * @description: 配置全局跨域
 * @author: 武汉旭瑞
 * @create: 2019-10-30 09:09
 **/

@Configuration
public class CorsConfig {

    private CorsConfiguration buildConfig() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin("*"); // 允许域名

        corsConfiguration.addAllowedHeader("*"); // 允许头

        corsConfiguration.addAllowedMethod("*"); // 允许方法

        return corsConfiguration;

    }

    @Bean
    public CorsFilter corsFilter() {

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();

        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", buildConfig());

        return new CorsFilter(urlBasedCorsConfigurationSource);

    }

}

