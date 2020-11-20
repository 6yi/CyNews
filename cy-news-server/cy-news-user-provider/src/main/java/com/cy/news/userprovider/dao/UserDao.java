package com.cy.news.userprovider.dao;

import com.cy.news.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserDao {
    int deleteByPrimaryKey(Integer uId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer uId);

    User selectByUserName(String userName);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}