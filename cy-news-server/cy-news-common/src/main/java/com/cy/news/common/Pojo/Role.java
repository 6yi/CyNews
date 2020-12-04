package com.cy.news.common.Pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * role
 * @author 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role implements Serializable {
    private Integer rId;

    private String roleName;

    private List<Permission> permissions;

    private static final long serialVersionUID = 1L;
}