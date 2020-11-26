package com.cy.news.common.Pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * comments
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comments implements Serializable {
    private Integer cId;

    private Integer nId;

    private Integer uId;

    private String uNickname;

    private Date cDate;

    private Integer cStatus;

    private String cContent;

    private static final long serialVersionUID = 1L;

}