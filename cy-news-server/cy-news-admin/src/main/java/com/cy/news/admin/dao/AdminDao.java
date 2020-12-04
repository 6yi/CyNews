package com.cy.news.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cy.news.common.Pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author yj
 * @ClassName AdminDao
 * @date 2020/11/30  15:30
 * @Version V1.0
 * @Description: TODO
 */
@Mapper
@Repository
public interface AdminDao extends BaseMapper<Admin> {

    @Select("select * from admin where a_username=#{adminName}")
    public Admin findByUsername(String adminName);
}
