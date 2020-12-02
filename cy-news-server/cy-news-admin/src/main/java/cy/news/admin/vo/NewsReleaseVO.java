package cy.news.admin.vo;

import lombok.*;
import org.w3c.dom.Text;

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
public class NewsReleaseVO {

    private String nTitle;

  //  private String nImg;

    private String nAuthor;

    private String nContent;

    private String tName;

}
