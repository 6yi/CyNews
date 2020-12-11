package com.cy.news.newsprovider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cy.news.common.Pojo.Comments;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName CommentsDao
 * @Author 6yi
 * @Date 2020/11/28 15:35
 * @Version 1.0
 * @Description:
 */
@Mapper
@Repository
public interface CommentsDao extends BaseMapper<Comments> {


    @Select("select * from comments where n_id=#{nId} and c_status<>-1 order by id")
    List<Comments> selectCommentsByNid(Long nId);

    @Insert("insert comments " +
            "(c_id,n_id,u_id,u_nickname,c_content,c_date,c_status) " +
            "values(#{comment.cId}," +
            "#{comment.nId}," +
            "#{comment.uNickname}," +
            "#{comment.cContent}," +
            "#{comment.cDate}," +
            "#{comment.cStatus})")
    int insertOneComments(Comments comment);

}
