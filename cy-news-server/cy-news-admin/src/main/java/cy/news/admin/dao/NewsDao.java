package cy.news.admin.dao;

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
 * @author yj
 * @ClassName NewsDao
 * @date 2020/11/30  15:26
 * @Version V1.0
 * @Description: TODO
 */
@Mapper
@Repository
public interface NewsDao extends BaseMapper<NewsWithBLOBs> {
    @Insert("insert into news(n_id,n_title,n_img,n_date,n_author,n_content,t_name,n_status) " +
                        "values(#{nId},#{nTitle},#{nImg},#{nDate},#{nAuthor},#{nContent},#{tName},#{nStatus})")
    public void insertNews(NewsWithBLOBs news);


}