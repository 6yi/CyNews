package com.cy.news.common.Pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * permission
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Permission implements Serializable {
    private Integer pId;

    private String power;

    private static final long serialVersionUID = 1L;

}