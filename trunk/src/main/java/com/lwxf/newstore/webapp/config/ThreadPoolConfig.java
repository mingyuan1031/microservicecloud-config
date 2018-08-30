package com.lwxf.newstore.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 功能：
 *
 * @author：renzhongshan(d3shan@126.com)
 * @create：2018-05-24 17:53:19
 * @version：2018 1.0
 * @company：老屋新房 Created with IntelliJ IDEA
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        //线程池维护线程的最小数量.
        threadPoolTaskExecutor.setCorePoolSize(20);
        //线程池维护线程的最大数量.
        threadPoolTaskExecutor.setMaxPoolSize(30);
        /**
         * workQueue队列的最大长度,
         * 工作时其会优先创建 CorePoolSize线程，当继续增加线程时，先放入Queue中，当 CorePoolSize和 Queue都满的时候，就增加创建新线程
         * 当线程达到MaxPoolSize的时候，就会抛出错误 org.springframework.core.task.TaskRejectedException)
         */
        threadPoolTaskExecutor.setQueueCapacity(1000);
        //线程池维护线程所允许的空闲时间
        threadPoolTaskExecutor.setKeepAliveSeconds(300);
        return threadPoolTaskExecutor;
    }
}
