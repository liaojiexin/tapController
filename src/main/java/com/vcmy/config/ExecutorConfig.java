package com.vcmy.config;

import com.vcmy.util.MyThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ExecutorConfig {

    @Bean("threadPool")
    public ThreadPoolTaskExecutor asyncServiceExecutor() {
        ThreadPoolTaskExecutor executor = new MyThreadPoolExecutor();
        //配置核心线程数-示例大小，按需配置
        executor.setCorePoolSize(10);
        //配置最大线程数-示例大小，按需配置
        executor.setMaxPoolSize(10);
        //配置空闲线程存活时间
        executor.setKeepAliveSeconds(60);
        //配置队列大小-示例大小，按需配置
        executor.setQueueCapacity(100);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("demo-Thread");
        // 配置拒绝策略：当pool已经达到max size的时候，如何处理新任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }
}
