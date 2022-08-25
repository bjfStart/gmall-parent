package com.atguigu.gmall.product.service;

import org.springframework.web.multipart.MultipartFile;


/**
 * @author feng
 * @create 2022-08-25 19:57
 */
public interface FileUploadService {
    String upload(MultipartFile file) throws  Exception;
}
