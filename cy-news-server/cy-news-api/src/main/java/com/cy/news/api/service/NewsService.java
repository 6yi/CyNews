package com.cy.news.api.service;

import com.cy.news.common.DTO.ResultDTO;

/**
 * @ClassName NewsService
 * @Author 6yi
 * @Date 2020/11/26 21:47
 * @Version 1.0
 * @Description:
 */

public interface NewsService {


    ResultDTO getNews(String type,Long start,Long end);

    ResultDTO getNewsContent(Long nId);





}
