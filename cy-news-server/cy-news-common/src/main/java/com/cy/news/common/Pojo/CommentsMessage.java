package com.cy.news.common.Pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * comments_message
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsMessage implements Serializable {
    private Integer mId;

    private Long cId;

    private Integer mLike;

    private static final long serialVersionUID = 1L;

}