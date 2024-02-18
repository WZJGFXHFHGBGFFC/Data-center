package org.wzj.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class SchedulerApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(SchedulerApplication.class, args);
        //项目初始化后打印出所有的bean
//        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
    }
}