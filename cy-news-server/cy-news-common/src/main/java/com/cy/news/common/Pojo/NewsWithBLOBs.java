package com.cy.news.common.Pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * news
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsWithBLOBs extends News implements Serializable {
    private Integer nId;

    private String nContent;

    private static final long serialVersionUID = 1L;

}