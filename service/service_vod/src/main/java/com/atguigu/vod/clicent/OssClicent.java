package com.atguigu.vod.clicent;

import com.atguigu.common_utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Component
@FeignClient(name = "service-oss",fallback = HystriDegrade.class)
public interface OssClicent {
    //定义调用的方法路径
    @ApiOperation(value = "上传图片到阿里云")
    @PostMapping(value = "/eduoss/fileoss/uploadOssFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R uploadOssFile(@RequestPart("file") MultipartFile file);

}
