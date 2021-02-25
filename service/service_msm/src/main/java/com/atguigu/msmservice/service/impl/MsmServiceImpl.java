package com.atguigu.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.common_utils.R;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(Map<String, Object> param, String phnone) {
        if (StringUtils.isEmpty(phnone)){
            return false;
        }
        DefaultProfile profile=DefaultProfile.getProfile("default","LTAI4G2qLLtRXF16TeefmRMo","KM8IXu8pgcy0nsR3kqORbWb7ockpak");
        IAcsClient client=new DefaultAcsClient(profile);
        //设置参数
        CommonRequest request=new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");

        //设置发送相关参数
        request.putQueryParameter("PhoneNumbers",phnone);//手机号
        request.putQueryParameter("SignName","ABC商城");//申请阿里云   签名名称
        request.putQueryParameter("TemplateCode","SMS_206735879");//申请阿里云   模板名称
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));//验证码数据，转换json数据传递
        //最终发送
        try {
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();
            return success;
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }



    }
}
