package com.cy.news.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName LoginUserVO
 * @Author 6yi
 * @Date 2020/11/20 17:58
 * @Version 1.0
 * @Description:
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserNameLoginUserVO {

    private String userName;

    private String passWord;

}
