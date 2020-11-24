package com.cy.news.emailserver.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cy.news.common.Pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AdminDao extends BaseMapper<Admin> {

    @Select("select *  from admin left join role on admin.rid = role.rid")
    public List<Admin> selectAllDev();

}
