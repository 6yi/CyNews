package cy.news.admin.service;

import com.cy.news.common.Pojo.News;
import com.cy.news.common.Pojo.NewsWithBLOBs;
import cy.news.admin.dao.NewsDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yj
 * @ClassName NewsService
 * @date 2020/12/2  19:20
 * @Version V1.0
 * @Description: TODO
 */
public class NewsService {
    @Autowired
    NewsDao newsDao;

    public void insertNews(News news){
        newsDao.insert();
    }
}
