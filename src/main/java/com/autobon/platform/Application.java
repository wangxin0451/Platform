package com.autobon.platform;


import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(scanBasePackages = {"com.autobon"})
@EntityScan(basePackages = {"com.autobon"})
@EnableJpaRepositories(basePackages = {"com.autobon"})
@EnableWebMvc
@EnableTransactionManagement
public class Application {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }
}
