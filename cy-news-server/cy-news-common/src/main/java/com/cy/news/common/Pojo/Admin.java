package com.cy.news.common.Pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * admin
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin implements Serializable {
    private Integer aId;

    private String aUsername;

    private String aPassword;

    private Integer aStatus;

    private Integer rId;

    private Role role;

    private static final long serialVersionUID = 1L;

}