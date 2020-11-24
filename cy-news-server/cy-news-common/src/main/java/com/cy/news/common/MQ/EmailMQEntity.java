package com.cy.news.common.MQ;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName EmailMQEntity
 * @Author 6yi
 * @Date 2020/11/22 19:24
 * @Version 1.0
 * @Description:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailMQEntity implements Serializable {

    private Integer uId;
    private String email;

}
