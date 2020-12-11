package com.cy.news.common.VO;

import lombok.*;

import java.io.Serializable;

/**
 * @ClassName CommentsVO
 * @Author 6yi
 * @Date 2020/12/6 1:20
 * @Version 1.0
 * @Description:
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentsVO implements Serializable {

    private Long nId;
    private String content;

}
