package com.cy.news.newsprovider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cy.news.common.Pojo.News;
import com.cy.news.common.Pojo.NewsMessage;
import com.cy.news.common.Pojo.NewsWithBLOBs;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;


/**
 * @ClassName NewsDao
 * @Author 6yi
 * @Date 2020/11/26 22:01
 * @Version 1.0
 * @Description:
 */

@Mapper
@Repository
public interface NewsDao extends BaseMapper<NewsWithBLOBs> {

    @SelectProvider(type = NewsDaoSqlProvider.class,method = "selectHotNews")
//    @Results({
//            @Result(column = "n_id",property = "nId",javaType = Integer.class),
//            @Result(column = "n_id"
//            ,property = "newsMessage"
//            ,javaType = NewsMessage.class
//            ,one = @One(select = "com.cy.news.newsprovider.dao.NewsMessageDao.findOneByNId"))})
    List<News> selectHotNews(@Param("nowDate") Date nowDate, @Param("lastDate") Date lastDate,@Param("type")String type);

    @SelectProvider(type = NewsDaoSqlProvider.class,method = "selectContentById")
    NewsWithBLOBs selectContentById(@Param("id") Long id);




    class NewsDaoSqlProvider{
        final String[] baseResult={"n_id","n_title","n_img","n_date","n_author","t_name","n_status"};
        final String[] contentResult={"n_id","n_content"};
        public String selectHotNews(Date nowDate,Date lastDate,String type){
            SQL sql = new SQL();
            sql.SELECT(baseResult).FROM("news");
            if(lastDate!=null){
                sql.WHERE("n_date<#{nowDate}").WHERE("n_date>#{lastDate}").WHERE("n_status=1");
            }else {
                sql.WHERE("n_date<#{nowDate}").WHERE("n_status=1");
            }
            if (type!=null){
                sql.WHERE("t_name=#{type}");
            }
            return sql.toString();
        }

        public String selectContentById(Long id){
            return new SQL().SELECT(contentResult).FROM("news").WHERE("n_id = #{id}").WHERE("n_status=1").toString();
        }
    }
}


