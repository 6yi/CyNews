package com.cy.news.newsprovider.service;

import com.cy.news.api.service.NewsMessageService;
import com.cy.news.api.service.UserService;
import com.cy.news.common.DTO.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName NewsMessageServiceImpl
 * @Author 6yi
 * @Date 2020/12/3 0:06
 * @Version 1.0
 * @Description:
 */

@DubboService(version = "1.0.0")
@Service
@Slf4j
public class NewsMessageServiceImpl implements NewsMessageService {

    @DubboReference(version = "1.0.0")
    private UserService userService;

    private static final Lock likeReentrantLock=new ReentrantLock();


    @Autowired
    private RedisTemplate<String, String> numberRedisTemplate;


    //todo 点赞
    @Override
    public ResultDTO incrLike(Long nId,Integer uId) {
        long number=1;
        if((numberRedisTemplate.opsForHash().get("newsMessage:"+nId, "like"))==null){
            try{
                likeReentrantLock.lock();
                if((numberRedisTemplate.opsForHash().get("newsMessage:"+nId, "like"))==null){
                    numberRedisTemplate.opsForHash().put("newsMessage:"+nId, "like","1");
                }else{
                    number=numberRedisTemplate.opsForHash().increment("newsMessage:"+nId, "like",1);
                }
            }finally {
                likeReentrantLock.unlock();
            }
        }else{
            number = numberRedisTemplate.opsForHash().increment("newsMessage:"+nId, "like",1);
        }
        userService.addUserLikeNews(nId,uId);
        return ResultDTO.builder().code(200).data(number).build();
    }

    @Override
    public ResultDTO decLike(Long nId, Integer uId) {
        long number=numberRedisTemplate.opsForHash().increment("newsMessage:"+nId, "like",-1);
        userService.delUserLikeNews(nId,uId);
        return ResultDTO.builder().code(200).data(number).build();
    }
}
