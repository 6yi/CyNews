package com.cy.news.admin.vo;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Text;

import java.io.Serializable;

/**
 * @author yj
 * @ClassName NewsReleaseVO
 * @date 2020/11/29  13:36
 * @Version V1.0
 * @Description: TODO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class NewsReleaseVO implements Serializable {

    private MultipartFile[] files;

    private String title;

  //  private String nImg;

    private String author;

    private String content;

    private String name;

}
