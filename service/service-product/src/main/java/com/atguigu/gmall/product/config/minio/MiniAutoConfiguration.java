package com.atguigu.gmall.product.config.minio;

import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author feng
 * @create 2022-08-25 20:29
 */
@Configuration
public class MiniAutoConfiguration {
    @Autowired
    MinioProperties minioProperties;

    @Bean
    public MinioClient minnioClient() throws InvalidPortException, InvalidEndpointException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException, RegionConflictException {
        //1.创建Minio客户端
        MinioClient minioClient = new MinioClient(minioProperties.getEndpoint(), minioProperties.getAk(), minioProperties.getSk());

        String bucketName = minioProperties.getBucketName();

        if(!minioClient.bucketExists(bucketName)){
            minioClient.makeBucket(bucketName);
        }

        return minioClient;
    }
}
