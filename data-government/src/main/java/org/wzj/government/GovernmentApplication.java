package org.wzj.government;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GovernmentApplication {
    public static void main(String[] args) {
        SpringApplication.run(GovernmentApplication.class,args);
    }
}