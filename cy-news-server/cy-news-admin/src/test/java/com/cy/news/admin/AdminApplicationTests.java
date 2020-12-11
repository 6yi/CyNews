package com.cy.news.admin;

import cn.hutool.core.convert.Convert;
import com.cy.news.common.Pojo.NewsWithBLOBs;
import com.cy.news.common.Utils.JWTUtils;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;


class AdminApplicationTests {

    @Test
    void test(){
        try {
            JWTUtils.verifiedJwt("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxIiwiaWF0IjoxNjA3NTczMzIwLCJleHAiOjE2MDc1NzUxMjB9.B76ixm1V98QdHJd9DuFgUU6aZjQCt30ZOFxsDWuHdy4");
        } catch (Exception e) {
            System.out.println("errpr");
        }
    }

    @Test
    void contextLoads() {


        String img=null;
        // 1 初始化用户身份信息（secretId, secretKey）。
        String secretId = "AKIDpSx3rRosjLq6WfeGiYAg28m05I9Jo4XC";
        String secretKey = "cS4xc3GvQghXilV61oMfpgMEwQiehqjN";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
// 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
// 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        // 指定要上传的文件
        File localFile = new File("f:/file/file");
        // 指定要上传到的存储桶
        String bucketName = "cy-news-1258023326";
        // 指定要上传到 COS 上对象键
        String key ="file";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);


    }


}
