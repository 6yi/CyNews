package com.cy.news.api.service;

import com.cy.news.common.DTO.ResultDTO;

/**
 * @ClassName NewsMessageService
 * @Author 6yi
 * @Date 2020/12/3 0:05
 * @Version 1.0
 * @Description:
 */

public interface NewsMessageService {

    ResultDTO incrLike(Long nId, Integer uId);

    ResultDTO decLike(Long nId,Integer uId);

}
