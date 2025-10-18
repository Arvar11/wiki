package com.panda.wiki.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisUtil {


    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 防重复校验
     * @param key 缓存键
     * @param second 过期时间(秒)
     * @return true-不重复(新建key) / false-重复(key已存在)
     */
    public boolean validateRepeat(String key, long second) {
        if (redisTemplate.hasKey(key)) {
            log.info("key已存在：{}", key);
            return false;  // 已存在，判定为重复
        } else {
            log.info("key不存在，放入：{}，过期{}秒", key, second);
            redisTemplate.opsForValue().set(key, key, second, TimeUnit.SECONDS);
            return true;   // 新建key，不重复
        }
    }
}