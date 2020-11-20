package com.cy.news.pojo.VO;

import com.cy.news.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName LoginSucessVO
 * @Author 6yi
 * @Date 2020/11/20 21:36
 * @Version 1.0
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginSuccessVO {

    private String JWT_TOKEN;
    private User user;

}
