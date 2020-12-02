package com.cy.news.newsprovider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cy.news.common.Pojo.NewsMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @ClassName NewsMessageDao
 * @Author 6yi
 * @Date 2020/11/27 0:35
 * @Version 1.0
 * @Description:
 */
@Mapper
@Repository
public interface NewsMessageDao extends BaseMapper<NewsMessage> {


    @Select("select * from news_message where n_id=#{nId}")
    NewsMessage findOneByNId(Long nId);


}
