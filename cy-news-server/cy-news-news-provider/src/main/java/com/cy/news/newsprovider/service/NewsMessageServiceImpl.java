package com.cy.news.newsprovider.service;

import com.cy.news.api.service.NewsMessageService;
import com.cy.news.api.service.UserService;
import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.newsprovider.Utils.RedisLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


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

    @Autowired
    private RedisLockUtil redisLockUtil;


    @Autowired
    private RedisTemplate<String, String> numberRedisTemplate;



    /**
     * @author 6yi
     * @Date 2020/12/11
     * @return
     * @Description:  给新闻点赞
     *
     **/
    @Override
    public ResultDTO incrLike(Long nId,Integer uId) {
        if(numberRedisTemplate.opsForHash().hasKey("userLike:" + uId, nId.toString())){
            // 已经点赞过了
            return ResultDTO.builder().code(200).data(-1).build();
        }

        //还未点赞
        long number=1;
        if((numberRedisTemplate.opsForHash().get("newsMessage:"+nId, "like"))==null){
            try{
                redisLockUtil.lock("like:lock",String.valueOf(Thread.currentThread().getId()));

                if((numberRedisTemplate.opsForHash().get("newsMessage:"+nId, "like"))==null){
                    numberRedisTemplate.opsForHash().put("newsMessage:"+nId, "like","1");
                }else{
                    number=numberRedisTemplate.opsForHash().increment("newsMessage:"+nId, "like",1);
                }
            }finally {
                redisLockUtil.unLock("like:lock",String.valueOf(Thread.currentThread().getId()));
            }
        }else{
            number = numberRedisTemplate.opsForHash().increment("newsMessage:"+nId, "like",1);
        }
        userService.addUserLikeNews(nId,uId);
        return ResultDTO.builder().code(200).data(number).build();
    }

    /**
     * @author 6yi
     * @date 2020/12/11
     * @return
     * @Description 取消点赞
     **/
    @Override
    public ResultDTO decLike(Long nId, Integer uId) {
        Long number=-1L;
        if (numberRedisTemplate.opsForHash().hasKey("userLike:"+uId, nId.toString())){
            number=numberRedisTemplate.opsForHash().increment("newsMessage:"+nId, "like",-1);
            userService.delUserLikeNews(nId,uId);
        }
        return ResultDTO.builder().code(200).data(number).build();
    }

}
