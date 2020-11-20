package com.cy.news.pojo.DTO;

import lombok.*;

import java.io.Serializable;

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
@Setter
@Getter
public class ResultDTO implements Serializable {

    Integer code;

    Object data;

}