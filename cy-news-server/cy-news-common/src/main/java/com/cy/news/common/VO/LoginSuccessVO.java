package com.cy.news.common.VO;

import com.cy.news.common.Pojo.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class LoginSuccessVO implements Serializable {

    private String JWT_TOKEN;
    private User user;

}
