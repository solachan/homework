package com.xiepuxin.incident;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication(scanBasePackages = {"com.xiepuxin.incident.*"})
@EnableCaching
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class IncidentApplication {

    public static void main(String[] args) {
        SpringApplication.run(IncidentApplication.class, args);
    }

}
