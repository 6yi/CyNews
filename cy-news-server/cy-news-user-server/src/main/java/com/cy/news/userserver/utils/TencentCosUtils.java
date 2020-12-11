package com.cy.news.userserver.utils;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.region.Region;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

/**
 * @ClassName TencentCosUtils
 * @Author 6yi
 * @Date 2020/12/7 21:31
 * @Version 1.0
 * @Description: 腾讯云oss上传工具
 */
@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TencentCosUtils {

    @Autowired
    private Snowflake snowflake;

    @Value("${oss.secretId}")
    private String secretId;
    @Value("${oss.secretKey}")
    private String secretKey;
    @Value("${oss.bucketName}")
    private String bucketName;
    @Value("${oss.regionName}")
    private String regionName;

    private COSClient cosClient;



    public void initClient(){
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(regionName);
        ClientConfig clientConfig = new ClientConfig(region);
        cosClient = new COSClient(cred, clientConfig);
    }




    public String upLoad(MultipartFile multipartFile){
        if(cosClient==null){
            initClient();
        }
        File file = transferToFile(multipartFile);
        String key=snowflake.nextIdStr()+file.getName();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        cosClient.putObject(putObjectRequest);
        return "https://"+bucketName+".cos."+regionName+".myqcloud.com/"+key;
    }


    public String upLoad(File file){
        if(cosClient==null){
            initClient();
        }
        String key=snowflake.nextIdStr()+file.getName();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file);
        cosClient.putObject(putObjectRequest);
        return "https://"+bucketName+".cos."+regionName+".myqcloud.com/"+key;
    }


    private File transferToFile(MultipartFile multipartFile) {
    // 选择用缓冲区来实现这个转换即使用java 创建的临时文件 使用 MultipartFile.transferto()方法 。
        File file = null;
        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String[] filename = originalFilename.split(".");
            file=File.createTempFile(filename[0], filename[1]);
            multipartFile.transferTo(file);
            file.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }



}
