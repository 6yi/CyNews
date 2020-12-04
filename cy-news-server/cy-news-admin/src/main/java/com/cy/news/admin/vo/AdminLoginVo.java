package com.cy.news.admin.vo;

import lombok.*;

/**
 * @author yj
 * @ClassName AdminLoginVo
 * @date 2020/11/26  23:10
 * @Version V1.0
 * @Description: TODO
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AdminLoginVo {


    private String adminName;

    private String passWord;
}
