package com.cy.news.MQserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cy.news.common.Pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminDao extends BaseMapper<Admin> {

    @Select("select *  from admin left join role on admin.r_id = role.r_id and role.role_name='dev'")
    List<Admin> selectAllDev();

}
