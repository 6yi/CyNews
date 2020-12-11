package com.cy.news.admin.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.convert.ConverterRegistry;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Snowflake;
import com.cy.news.admin.service.NewsService;
import com.cy.news.admin.utils.Page;
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
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

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

    @Autowired
    StringRedisTemplate redisTemplate;

    @Value("cos.secretid")
    String secretId ;

    @Value("cos.secretkey")
    String secretKey ;

    @Value("cos.region")
    String regionId;

    @Value("cos.bucketName")
    String bucketName ;

    @Autowired
    NewsService newsService;

    @PostMapping("/news/newsRelease")
    public ResultDTO newsRelease(@RequestBody NewsReleaseVO newsReleaseVO){

       System.out.println(newsReleaseVO.getImg());

       if(
                newsReleaseVO==null||
                newsReleaseVO.getAuthor()==null||
                newsReleaseVO.getContent()==null||
                newsReleaseVO.getName()==null||
                newsReleaseVO.getTitle()==null
        ){
            return ResultDTO.builder().code(NewsRetError.NEWSREALSASE_ERROR).data("信息输入错误").build();
        }

            newsService.newsRelease(newsReleaseVO);

            redisTemplate.delete("hotNews:index");

        return ResultDTO.builder().code(NewsRetError.OK).data("发布成功").build();
    }




    @PostMapping("/news/showNewsList")
    public ResultDTO showNewsList(@RequestBody  Page page){


        return newsService.showNewsList(page);
    }

    @RequestMapping("/photo")

    public ResultDTO photo(@RequestBody() MultipartFile file){

        File cFile=new File("F:/file/"+file.getOriginalFilename());

        String key=null;

        try {
            FileUtils.copyInputStreamToFile(file.getInputStream(), cFile);

            COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
            // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
            // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
            Region region = new Region(regionId);

            ClientConfig clientConfig = new ClientConfig(region);
            // 3 生成 cos 客户端。
            COSClient cosClient = new COSClient(cred, clientConfig);

            // 指定要上传的文件
            File localFile = cFile;

            key= String.valueOf(snowflake.nextId())+cFile.getName();

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);

            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultDTO.builder().code(NewsRetError.OK).data("https://cy-news-1258023326.cos.ap-guangzhou.myqcloud.com/"+key).build();

    }
}
