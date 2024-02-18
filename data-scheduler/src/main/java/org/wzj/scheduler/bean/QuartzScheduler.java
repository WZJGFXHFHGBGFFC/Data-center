package org.wzj.scheduler.bean;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzScheduler {
    private static Scheduler scheduler;

    // 静态代码块，在类加载时初始化scheduler对象
    static {
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
        } catch (Exception e) {
            throw new RuntimeException("Initializing scheduler failed", e);
        }
    }

    // 获取scheduler对象的静态方法
    public static Scheduler getScheduler() {
        return scheduler;
    }
}
