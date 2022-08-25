package com.atguigu.gmall.product.controller;

import com.atguigu.gmall.common.result.Result;
import com.atguigu.gmall.product.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author feng
 * @create 2022-08-25 19:54
 */
@Api(tags = "文件上传控制器")
@RequestMapping("/admin/product")
@RestController
public class FileUploadController {
    @Autowired
    FileUploadService fileUploadService;

    @ApiOperation("文件上传")
    @PostMapping("fileUpload")
    public Result fileupload(@RequestPart("file")MultipartFile file) throws Exception {
        //收到前端的文件流，上传给 Minio，并返回这个文件在Minio中存储地址
        //以后用这个地址访问，数据库保存的也是这个地址
        String url = fileUploadService.upload(file);
        return Result.ok(url);
    }


}
