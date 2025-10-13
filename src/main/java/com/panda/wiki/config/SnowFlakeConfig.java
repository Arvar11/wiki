package com.panda.wiki.config;

import com.panda.wiki.util.SnowFlake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowFlakeConfig {
    
    @Bean
    public SnowFlake snowFlake() {
        // 这里可以读取配置文件，现在先用固定值
        // 数据中心ID=1，机器ID=1
        return new SnowFlake(1, 1);
    }
}