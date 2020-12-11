package com.cy.news.newsprovider.service;

import cn.hutool.core.lang.Snowflake;
import com.cy.news.api.service.CommentsService;
import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.common.Pojo.Comments;
import com.cy.news.newsprovider.Utils.RedisLockUtil;
import com.cy.news.newsprovider.dao.CommentsDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @ClassName CommentsServiceImpl
 * @Author 6yi
 * @Date 2020/12/5 15:25
 * @Version 1.0
 * @Description:
 */

@Slf4j
@Service
@DubboService(version = "1.0.0")
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private RedisTemplate<Object, Object> jsonRedisTemplate;

    @Autowired
    private CommentsDao commentsDao;

    @Autowired
    private RedisLockUtil redisLockUtil;

    @Autowired
    private Snowflake snowflake;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Value("${mq.news.topic}")
    private String topic;

    @Value("${mq.news.tag.send}")
    private String tag;

    /**
     * @author 6yi
     * @date 2020/12/11
     * @return
     * @Description 添加评论
     *
     **/
    @Override
    public ResultDTO addComments(Long nId, Integer uId,String nickName ,String content) {
        Comments comments = Comments.builder()
                .cId(snowflake.nextId())
                .cContent(content)
                .nId(nId)
                .uId(uId)
                .uNickname(nickName)
                .cDate(new Date())
                .cStatus(1)
                .build();
        String key = "comments:"+nId;
        jsonRedisTemplate.opsForList().leftPush(key,comments);
        sendCommentMQ(comments);
        return ResultDTO.builder().code(200).build();
    }


    /**
     * @author 6yi
     * @date 2020/12/5
     * @return
     * @Description 发送延迟消息去处理评论，隔一段时间再刷回数据库
     **/
    public void sendCommentMQ(Comments comments){
        try{
            Message message = new Message(topic,
                    tag,
                    comments.getCId().toString(),
                    new ObjectMapper().writeValueAsString(comments).getBytes());
          message.setDelayTimeLevel(16);
          rocketMQTemplate.getProducer().send(message);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }




    /**
     * @author 6yi
     * @date 2020/11/28
     * @return
     * @Description 分页查询评论
     **/
    @Override
    public ResultDTO getComments(Long nId, Integer page,Integer number) {
        String key = "comments:"+nId;
        long start=page*number;
        long end=start+number;
        List<Object> comments = jsonRedisTemplate.opsForList().range(key, start, end);

        if(comments==null||comments.isEmpty()){
            try{
                redisLockUtil.lock("Comments:lock",String.valueOf(Thread.currentThread().getId()));
                comments=jsonRedisTemplate.opsForList().range(key, start, end);
                if(comments==null||comments.isEmpty()){
                    return getAtDataBase(nId, page, number, key);
                }
            }catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }finally{
                redisLockUtil.unLock("Comments:lock",String.valueOf(Thread.currentThread().getId()));
            }
        }

        return ResultDTO.builder().code(200).data(comments).build();
    }




    /**
     * @author 6yi
     * @date 2020/12/5
     * @return
     * @Description 从数据库获取
     **/
    private ResultDTO getAtDataBase(Long nId, Integer page, Integer number, String key) {
        PageHelper.offsetPage(page,number);
        List<Comments> list = commentsDao.selectCommentsByNid(nId);
        list.forEach(h->{
            jsonRedisTemplate.opsForList().leftPush(key,h);
        });
        jsonRedisTemplate.expire(key,3, TimeUnit.HOURS);
        return ResultDTO.builder().code(200).data(list).build();
    }


}