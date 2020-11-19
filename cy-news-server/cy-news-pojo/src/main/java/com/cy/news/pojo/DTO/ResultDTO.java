package com.cy.news.pojo.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ResultDTO
 * @Author 6yi
 * @Date 2020/11/19 20:15
 * @Version 1.0
 * @Description:
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {

    Integer code;

    Object data;

}
