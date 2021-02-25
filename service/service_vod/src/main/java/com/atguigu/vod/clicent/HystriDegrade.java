package com.atguigu.vod.clicent;

import com.atguigu.common_utils.R;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class HystriDegrade implements OssClicent {
    @Override
    public R uploadOssFile(MultipartFile file) {
        return R.error().data("erromeassge","这是hystrix出错兜底的方法");
    }
}
