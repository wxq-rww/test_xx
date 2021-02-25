package com.atguigu.oss.controller;

import com.atguigu.common_utils.R;
import com.atguigu.oss.service.OssService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @ApiOperation(value = "上传图片到阿里云")
    @PostMapping(value = "/uploadOssFile")
    public R uploadOssFile(@RequestPart("file") MultipartFile file){
    String url   =  ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);

    }





}
