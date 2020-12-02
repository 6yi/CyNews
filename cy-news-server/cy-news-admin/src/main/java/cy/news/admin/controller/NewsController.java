package cy.news.admin.controller;

import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.common.Pojo.News;

import com.cy.news.common.Pojo.NewsMessage;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import cy.news.admin.dao.NewsDao;
import cy.news.admin.exception.NewsRetError;
import cy.news.admin.vo.NewsReleaseVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

/**
 * @author yj
 * @ClassName NewsController
 * @date 2020/11/26  23:07
 * @Version V1.0
 * @Description: TODO
 */
@RestController
public class NewsController {

    @PostMapping("/newsRelease")
    public ResultDTO newsRelease(@RequestBody NewsReleaseVO newsReleaseVO, @RequestParam(required=false,value = "file") File[] files){
        String  img=null;
        if(newsReleaseVO==null||
                newsReleaseVO.getNAuthor()==null||
                newsReleaseVO.getNContent()==null||
               // newsReleaseVO.getNImg()==null||
                newsReleaseVO.getTName()==null||
                newsReleaseVO.getNTitle()==null
        ){
            return ResultDTO.builder().code(NewsRetError.NEWSREALSASE_ERROR).data("信息输入错误").build();
        }

        if(files==null){
            return ResultDTO.builder().code(NewsRetError.OK).data("无标题图片").build();

        }else {
            int length = files.length;

            for (int i = 0; i <= length; i++) {
//                    // 1 初始化用户身份信息（secretId, secretKey）。
//                    String secretId = "AKIDpSx3rRosjLq6WfeGiYAg28m05I9Jo4XC";
//                    String secretKey = "cS4xc3GvQghXilV61oMfpgMEwQiehqjNcS4xc3GvQghXilV61oMfpgMEwQiehqjN";
//                    COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
//                    // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
//                    // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
//                    Region region = new Region("ap-guangzhou");
//                    ClientConfig clientConfig = new ClientConfig(region);
//                    // 3 生成 cos 客户端。
//                    COSClient cosClient = new COSClient(cred, clientConfig);
//
//                    // 指定要上传的文件
//                    File localFile = files[i];
//                    // 指定要上传到的存储桶
//                    String bucketName = "cy-news-1258023326";
//                    // 指定要上传到 COS 上对象键
//                    String key = newsReleaseVO.getNTitle()+i+".jpg";
//                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
//                    PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
                Cos(i, files[i], newsReleaseVO);
                String key = Cos(i, files[i], newsReleaseVO);
                img = img + "https://cy-news-1258023326.cos.ap-guangzhou.myqcloud.com/" + key;
                if (i < length) {
                    img = img + "|";
                }

            }
        }

            News news = News.builder().
                    nAuthor(newsReleaseVO.getNAuthor()).
                    nDate(new Date()).
                    nImg(img).
                    nTitle(newsReleaseVO.getNTitle()).
                    tName(newsReleaseVO.getTName()).
                    nStatus(1).build();


        NewsMessage newsMessage= NewsMessage.builder().nId(news.getNId()).mLike(0).mWatch(0).build();

        return ResultDTO.builder().code(NewsRetError.OK).data("发布成功").build();
    }



    public String Cos(Integer  i,File file,NewsReleaseVO   newsReleaseVO){

        String img=null;
        // 1 初始化用户身份信息（secretId, secretKey）。
        String secretId = "AKIDpSx3rRosjLq6WfeGiYAg28m05I9Jo4XC";
        String secretKey = "cS4xc3GvQghXilV61oMfpgMEwQiehqjNcS4xc3GvQghXilV61oMfpgMEwQiehqjN";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        // 指定要上传的文件
        File localFile = file;
        // 指定要上传到的存储桶
        String bucketName = "cy-news-1258023326";
        // 指定要上传到 COS 上对象键
        String key = newsReleaseVO.getNTitle()+i+".jpg";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        return key;

    }

}
