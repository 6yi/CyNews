package com.cy.news.newsprovider.service;

import com.cy.news.api.service.NewsService;
import com.cy.news.common.DTO.ResultDTO;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @ClassName NewsServiceImpl
 * @Author 6yi
 * @Date 2020/11/26 21:48
 * @Version 1.0
 * @Description:
 */

@DubboService(version = "1.0.0")
public class NewsServiceImpl implements NewsService {

    @Override
    public ResultDTO getIndexNews() {




        return null;
    }


}
