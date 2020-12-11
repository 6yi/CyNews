package com.cy.news.userprovider.dao;

import com.cy.news.common.Pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
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

    int updateUserStatusById(Integer userStatusCoder,Integer userId);


    User findUserByMail(String mail);

    @Update("update user set u_avatar=#{imgSrc} where u_id=#{uId}")
    int upLoadAvatar(String imgSrc,Integer uId);


}