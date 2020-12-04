package com.cy.news.test;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.nacos.common.utils.MD5Utils;
import com.cy.news.api.service.UserService;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class TestApplicationTests {

//    @DubboReference(version = "1.0.0")
//    private UserService userService;


    @Test
    void contextLoads2() {
        Jedis jedis = new Jedis();
        jedis.hset("hash","field", "1");
        jedis.hincrBy("hash","field",1);
    }



//    @Autowired
//    private StringRedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        String pwd = "admin";
        String s = SecureUtil.md5(pwd);
        System.out.println(s);
//        redisTemplate.opsForValue().set("mail:lzheng","aasdasd",1L, TimeUnit.SECONDS);

    }

    public static void main(String[] args) {

        String secretId = " AKIDpSx3rRosjLq6WfeGiYAg28m05I9Jo4XC";
        String secretKey = "cS4xc3GvQghXilV61oMfpgMEwQiehqjN";

        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cred, clientConfig);

        File file = new File("C:\\Users\\6yi\\Pictures\\Saved Pictures\\49f5e9cdly1g2slfi12onj215o0nrwnr.jpg");

        String bucketName = "cy-news-1258023326";

        String key = file.getName();

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        System.out.println(putObjectResult.getRequestId());


    }





}
