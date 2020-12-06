package com.cy.news.newsprovider.Utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedisTool
 * @Author 6yi
 * @Date 2020/12/5 13:09
 * @Version 1.0
 * @Description:
 */


@Slf4j
@Component
public class RedisLockUtil {
    /*** 分布式锁固定前缀 ***/
    private static final String REDIS_LOCK = "redis_lock_";
    /*** 分布式锁过期时间 ***/
    private static final Integer EXPIRE_TIME = 30;
    /*** 每次自旋睡眠时间 ***/
    private static final Integer SLEEP_TIME = 50;
    /*** 分布式锁自旋次数 ***/
    private static final Integer CYCLES = 10;

    @SuppressWarnings("all")
    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> lockOperations;

    /**
     * 加锁
     *
     * @param key   加锁唯一标识
     * @param value 释放锁唯一标识（建议使用线程ID作为value）
     */
    public void lock(String key, String value) {
        lock(key, value, EXPIRE_TIME);
    }

    /**
     * 加锁
     * @param key     加锁唯一标识
     * @param value   释放锁唯一标识（建议使用线程ID作为value）
     * @param timeout 超时时间（单位：S）
     */
    public void lock(String key, String value, Integer timeout) {
//        Assert.isTrue(StringUtils.isNotBlank(key), "redis locks are identified as null.");
//        Assert.isTrue(StringUtils.isNotBlank(value), "the redis release lock is identified as null.");

        int cycles = CYCLES;
        // ----- 尝试获取锁，当获取到锁，则直接返回，否则，循环尝试获取
        while (!tryLock(key, value, timeout)) {
            // ----- 最多循环10次，当尝试了10次都没有获取到锁，抛出异常
            if (0 == (cycles--)) {
                log.error("redis try lock fail. key: {}, value: {}", key, value);
                throw new RuntimeException("redis try lock fail.");
            }
            try {
                TimeUnit.MILLISECONDS.sleep(SLEEP_TIME);
            } catch (Exception e) {
                log.error("history try lock error.", e);
            }
        }
    }

    /**
     * 尝试获取锁
     * @param key     加锁唯一标识
     * @param value   释放锁唯一标识（建议使用线程ID作为value）
     * @param timeout 超时时间（单位：S）
     * @return [true: 加锁成功; false: 加锁失败]
     */
    private boolean tryLock(String key, String value, Integer timeout) {
        Boolean result = lockOperations.setIfAbsent(REDIS_LOCK + key, value, timeout, TimeUnit.SECONDS);
        return result != null && result;
    }

    /**
     * 释放锁
     * @param key   加锁唯一标识
     * @param value 释放锁唯一标识（建议使用线程ID作为value）
     */
    public void unLock(String key, String value) {
//        Assert.isTrue(StringUtils.isNotBlank(key), "redis locks are identified as null.");
//        Assert.isTrue(StringUtils.isNotBlank(value), "the redis release lock is identified as null.");
        key = REDIS_LOCK + key;
        // ----- 通过value判断是否是该锁：是则释放；不是则不释放，避免误删
        if (value.equals(lockOperations.get(key))) {
            lockOperations.getOperations().delete(key);
        }
    }
}