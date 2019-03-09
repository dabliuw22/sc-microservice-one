
package com.leysoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

import com.leysoft.service.inter.CustomMessageSource;

@EnableFeignClients(
        value = {
            "com.leysoft.client"
        })
@EnableHystrix
@EnableHystrixDashboard
@EnableDiscoveryClient
@SpringBootApplication
@EnableBinding(
        value = {
            Source.class, CustomMessageSource.class
        })
public class ScMicroserviceOneApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScMicroserviceOneApplication.class, args);
    }
}
