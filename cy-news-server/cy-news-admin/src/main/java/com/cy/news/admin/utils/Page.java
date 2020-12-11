package com.cy.news.admin.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yj
 * @ClassName Page
 * @date 2020/12/7  15:58
 * @Version V1.0
 * @Description: TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Page {

    private int currentPage;
    private int pageSize;

}
