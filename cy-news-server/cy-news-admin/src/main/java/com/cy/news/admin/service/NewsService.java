package com.cy.news.admin.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.cy.news.admin.exception.NewsRetError;
import com.cy.news.admin.utils.Page;
import com.cy.news.admin.vo.NewsReleaseVO;
import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.common.Pojo.News;
import com.cy.news.common.Pojo.NewsWithBLOBs;
import com.cy.news.admin.dao.NewsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author yj
 * @ClassName NewsService
 * @date 2020/12/2  19:20
 * @Version V1.0
 * @Description: TODO
 */
@Service
public class NewsService {
    @Autowired
    NewsDao newsDao;
    @Autowired
    Snowflake snowflake;
    public void insertNews(News news){
//        newsDao.insertNews(news);
    }

    public void newsRelease(NewsReleaseVO newsReleaseVO){


        NewsWithBLOBs newsWithBLOBs=new NewsWithBLOBs();
        newsWithBLOBs.setNId(snowflake.nextId());
        newsWithBLOBs.setNStatus(newsReleaseVO.getStatus());
        newsWithBLOBs.setTName(newsReleaseVO.getName());
        newsWithBLOBs.setNTitle(newsReleaseVO.getTitle());
        newsWithBLOBs.setNImg(newsReleaseVO.getImg());
        newsWithBLOBs.setNContent(newsReleaseVO.getContent());
        newsWithBLOBs.setNAuthor(newsReleaseVO.getAuthor());
        newsWithBLOBs.setNDate(DateUtil.date(System.currentTimeMillis()));

        newsDao.insertNews(newsWithBLOBs);
    }


    public ResultDTO showNewsList(Page page){


        int pageStart=(page.getCurrentPage()-1)*page.getPageSize();

        int total=newsDao.getNewsNumber();

        HashMap<String,Object> res=new HashMap<>();

        res.put("page",page);

        res.put("total",total);

        List<NewsWithBLOBs> newsWithBLOBs = newsDao.showNewsList(pageStart,page.getPageSize());

        res.put("newsWithBLOBs",newsWithBLOBs);

        return ResultDTO.builder().data(res).code(NewsRetError.OK).build();
    }
}
