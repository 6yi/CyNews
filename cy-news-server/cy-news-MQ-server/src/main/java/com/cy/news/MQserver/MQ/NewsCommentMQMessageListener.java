package com.cy.news.MQserver.MQ;

import com.cy.news.MQserver.dao.CommentsDao;
import com.cy.news.common.Pojo.Comments;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName NewsCommentMQMessageListener
 * @Author 6yi
 * @Date 2020/12/5 23:39
 * @Version 1.0
 * @Description: 刷评论回数据库
 */

@Slf4j
@Component
@RocketMQMessageListener(topic = "${mq.news.topic}",consumerGroup = "${rocketmq.producer.Newe.group}")
public class NewsCommentMQMessageListener implements RocketMQListener<MessageExt> {

    @Autowired
    private CommentsDao commentsDao;

    @Override
    public void onMessage(MessageExt messageExt) {
        try{
            String jsonString = new String(messageExt.getBody(), "UTF-8");
            Comments comments = new ObjectMapper().readValue(jsonString, Comments.class);
            commentsDao.insertOneComments(comments);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
