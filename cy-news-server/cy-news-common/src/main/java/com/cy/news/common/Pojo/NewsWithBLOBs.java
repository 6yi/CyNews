package com.cy.news.common.Pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsWithBLOBs extends News implements Serializable {


    private String nContent;

    private static final long serialVersionUID = 1L;



}