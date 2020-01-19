package com.suree.hw_module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HwModuleApplication {

	public static void main(String[] args) {
		SpringApplication.run(HwModuleApplication.class, args);
	}

}
