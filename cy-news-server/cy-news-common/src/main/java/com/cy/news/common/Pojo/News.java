package com.cy.news.common.Pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.util.Date;

/**
 * news
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class News implements Serializable {



    private Long nId;

    private String nTitle;

    private Date nDate;

    private String nAuthor;

    private String tName;

    private Integer nStatus;

    private String nImg;

    private String[] imgSrc;

    private NewsMessage newsMessage;

    private Long hotKey;

    private static final long serialVersionUID = 1L;

}