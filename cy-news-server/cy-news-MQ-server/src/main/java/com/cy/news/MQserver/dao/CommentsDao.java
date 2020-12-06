package com.cy.news.MQserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cy.news.common.Pojo.Comments;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface CommentsDao extends BaseMapper<Comments> {


    @Select("select * from comments where n_id=#{nId} and c_status<>-1 order by id")
    List<Comments> selectCommentsByNid(Long nId);

    @Insert("insert comments " +
            "(c_id,n_id,u_id,u_nickname,c_content,c_date,c_status) " +
            "values(#{comment.cId}," +
            "#{comment.nId}," +
            "#{comment.uId},"+
            "#{comment.uNickname}," +
            "#{comment.cContent}," +
            "#{comment.cDate}," +
            "#{comment.cStatus})")
    int insertOneComments(@Param("comment") Comments comment);

}