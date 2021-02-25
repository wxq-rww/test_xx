package com.atguigu.msmservice.controller;

import com.atguigu.common_utils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api("发送短信")
@RestController
@RequestMapping("/msm")
@CrossOrigin
public class MsmController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("发送短信")
    //发送短信的方法
    @GetMapping("send/{phnone}")
    public R senMsm(@PathVariable String phnone) {
        //从redis中获取验证码，如果获取到直接返回
        String code = (String) redisTemplate.opsForValue().get(phnone);
        //如何redis中取不到，进行阿里云发送
        if (!StringUtils.isEmpty(code)){
            return  R.ok();
        }



        //生成一个随机值，传递阿里云进行发送
        code= RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code",code);
        //调用service中发送短信的方法
      boolean issend=  msmService.send(param,phnone);
            if (issend){
                redisTemplate.opsForValue().set(phnone,code,5, TimeUnit.MINUTES);
                return R.ok();
            }else {
                return  R.error().message("短信发送失败");
            }



    }


}
