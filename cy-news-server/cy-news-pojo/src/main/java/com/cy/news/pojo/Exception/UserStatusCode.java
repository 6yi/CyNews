package com.cy.news.pojo.Exception;

import lombok.Data;

/**
 * @ClassName UserStatusCode
 * @Author 6yi
 * @Date 2020/11/20 21:02
 * @Version 1.0
 * @Description:
 */

@Data
public class UserStatusCode {

    public static final Integer OK = 1;
    public static final Integer BAN = -1;
    public static final Integer NOT_ACTIVATED = 0;
    public static final Integer DELETED = -2;

}
