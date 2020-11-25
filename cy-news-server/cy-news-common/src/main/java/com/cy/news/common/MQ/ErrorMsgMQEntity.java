package com.cy.news.common.MQ;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ErrorMQEntity
 * @Author 6yi
 * @Date 2020/11/25 11:17
 * @Version 1.0
 * @Description:
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMsgMQEntity {

    String time;

    String errorMsg;

}
