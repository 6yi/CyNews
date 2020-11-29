package com.cy.news.common.Pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * news_message
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsMessage implements Serializable {
    private Integer mId;

    private Long nId;

    private Integer mLike;

    private Integer mWatch;

    private static final long serialVersionUID = 1L;

}