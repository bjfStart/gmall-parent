package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.common.util.DateUtil;
import com.atguigu.gmall.product.config.minio.MinioProperties;
import com.atguigu.gmall.product.service.FileUploadService;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @author feng
 * @create 2022-08-25 19:57
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    MinioProperties minioProperties;

    @Autowired
    MinioClient minioClient;

    @Override
    public String upload(MultipartFile file) throws Exception {
        //文件流
        //得到唯一的文件名
        String dateStr = DateUtil.formatDate(new Date());
        String filename = UUID.randomUUID().toString().replace("-", "") + "_" + file.getOriginalFilename(); //原始文件名
        InputStream inputStream = file.getInputStream();
        String contentType = file.getContentType();
        //文件上传参数：long objectSize, long partSize
        PutObjectOptions options = new PutObjectOptions(file.getSize(), -1L);
        options.setContentType(contentType);
        //告诉Minio上传的这个文件的内容类型
        minioClient.putObject(minioProperties.getBucketName(),
                dateStr + "/" + filename,
                inputStream,
                options
        );

        //返回刚才上传的url
        String url = minioProperties.getEndpoint() + "/" + minioProperties.getBucketName() + "/" + dateStr + "/" + filename;
        return url;

    }
}
