package com.cy.news.pojo.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterUserByEmailVO implements Serializable {

    private String userName;

    private String passWord;

    private String nickName;

    private String email;

    private String confirmPassword;


}
