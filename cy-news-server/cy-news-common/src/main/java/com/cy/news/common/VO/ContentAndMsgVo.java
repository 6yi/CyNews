package com.cy.news.common.VO;

import com.cy.news.common.Pojo.NewsMessage;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName ContentAndMsgVo
 * @Author 6yi
 * @Date 2020/12/2 22:04
 * @Version 1.0
 * @Description:
 */

@Data
@Builder
public class ContentAndMsgVo implements Serializable {

    private String content;
    private NewsMessage newsMessage;

}
