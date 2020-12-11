package com.cy.news.newsprovider.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @ClassName KeyExpiredListener
 * @Author 6yi
 * @Date 2020/12/2 14:10
 * @Version 1.0
 * @Description: 过期的key监听
 */

@Slf4j
@Component
public class KeyExpiredListener extends KeyExpirationEventMessageListener {
    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
        //过期的key
        String key = new String(message.getBody(),StandardCharsets.UTF_8);

        log.info("redis key 过期：pattern={},channel={},key={}",new String(pattern),channel,key);

    }
}
