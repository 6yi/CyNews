package com.cy.news.admin.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.cy.news.common.DTO.ResultDTO;
import com.cy.news.common.Pojo.News;
import com.cy.news.common.Pojo.NewsMessage;
import com.cy.news.common.Pojo.NewsWithBLOBs;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.cy.news.admin.dao.NewsDao;
import com.cy.news.admin.exception.NewsRetError;
import com.cy.news.admin.vo.NewsReleaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

/**
 * @author yj
 * @ClassName NewsController
 * @date 2020/11/26  23:07
 * @Version V1.0
 * @Description: TODO
 */
@RestController
@CrossOrigin(allowCredentials="true",maxAge = 3600)
public class NewsController {
    @Autowired
    NewsDao newsDao;
    @Autowired
    Snowflake snowflake;

    @PostMapping("/newsRelease")
    public ResultDTO newsRelease(@RequestBody NewsReleaseVO newsReleaseVO){
        String  img=null;

        if(newsReleaseVO==null||
                newsReleaseVO.getAuthor()==null||
                newsReleaseVO.getContent()==null||
               // newsReleaseVO.getNImg()==null||
                newsReleaseVO.getName()==null||
                newsReleaseVO.getTitle()==null
        ){
            return ResultDTO.builder().code(NewsRetError.NEWSREALSASE_ERROR).data("信息输入错误").build();
        }


        NewsWithBLOBs newsWithBLOBs=new NewsWithBLOBs();
        newsWithBLOBs.setNId(snowflake.nextId());


        MultipartFile[] MultipartFile=  newsReleaseVO.getFiles();

        if(MultipartFile==null){
            return ResultDTO.builder().code(NewsRetError.OK).data("无标题图片").build();

        }else {
            img="";
            int length = MultipartFile.length;

            for (int i = 0; i <= length; i++) {
                ConverterRegistry converterRegistry = ConverterRegistry.getInstance();
                File file=converterRegistry.convert(File.class,MultipartFile[i]);
                String key = Cos(i, file, newsWithBLOBs);
                img = img + "https://cy-news-1258023326.cos.ap-guangzhou.myqcloud.com/" + key;
                if (i < length) {
                    img = img + "|";
                }

            }
        }

            newsWithBLOBs.setNStatus(1);
            newsWithBLOBs.setTName(newsReleaseVO.getName());
            newsWithBLOBs.setNTitle(newsReleaseVO.getTitle());
            newsWithBLOBs.setNImg(null);
            newsWithBLOBs.setNContent(newsReleaseVO.getContent());
            newsWithBLOBs.setNAuthor(newsReleaseVO.getAuthor());
            newsWithBLOBs.setNDate(DateUtil.date());




            newsDao.insertNews(newsWithBLOBs);





        return ResultDTO.builder().code(NewsRetError.OK).data("发布成功").build();
    }



    public String Cos(Integer  i,File file,NewsWithBLOBs   newsWithBLOBs){

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
        String key =newsWithBLOBs.getNId()+ newsWithBLOBs.getNTitle()+"("+ Convert.toStr(i + 1)+")"+".jpg";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        return key;

    }

}
