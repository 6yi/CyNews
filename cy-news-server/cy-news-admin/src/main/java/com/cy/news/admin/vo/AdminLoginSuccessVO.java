package com.cy.news.admin.vo;

import com.cy.news.common.Pojo.Admin;
import com.cy.news.common.Pojo.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yj
 * @ClassName LoginSuccessVO
 * @date 2020/12/5  15:50
 * @Version V1.0
 * @Description: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminLoginSuccessVO {

    private String JWT_TOKEN;
    private Admin admin;

}
