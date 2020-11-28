package com.cy.news.common.Pojo;

import lombok.*;
import lombok.experimental.ExtensionMethod;

import java.io.Serializable;
import java.util.Date;

/**
 * news
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NewsWithBLOBs extends News implements Serializable {


    private String nContent;

    private static final long serialVersionUID = 1L;



}