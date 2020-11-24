package com.cy.news.common.VO;

import lombok.*;

import java.io.Serializable;

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
@Setter
@Getter
public class UserNameLoginVO implements Serializable {

    private String userName;

    private String passWord;

}
