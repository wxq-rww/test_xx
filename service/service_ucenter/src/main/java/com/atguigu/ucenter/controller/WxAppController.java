package com.atguigu.ucenter.controller;

import com.atguigu.ucenter.utils.ConstantWxUtils;
import com.atguigu.ucenter.utils.HttpClientUtils;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller
@RequestMapping("/api/ucenter/wx")
public class WxAppController {

    //1.生成微信二维码
    @GetMapping("/erma")
    public String erma() {
        // 微信开放平台授权baseUrl  %s相当于?代表占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //对redirect_uri进行URLEncode编码
        String redirect_uri = ConstantWxUtils.WX_OPEN_REDIRECT_URL;
        try {
            redirect_uri = URLEncoder.encode(redirect_uri, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        //设置%s里面的值
        String url = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_REDIRECT_URL,
                "atgui"
        );

//重定向到请求微信地址里面
        return "redirect:" + url;
    }


    //2 获取扫描人信息，添加数据
    @GetMapping("/callback")
    public String callback(String code, String state) {

        try {
            //1.获取code值，临时票据，类似于验证码

            //2.拿着code请求 微信固定的地址值，得到两个值access_token和openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接三个参数 ：id  秘钥 和 code值
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWxUtils.WX_OPEN_APP_ID,
                    ConstantWxUtils.WX_OPEN_APP_SECRET,
                    code
            );
            //请求这个拼接好的地址，返回两个值access_token和openid
            //使用httpclient发送请求，得到返回结果

            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);

         //从accessTokenInfo字符串获取出来两个值access_token和openid
            //把accessTokenInfo字符串转换成map集合，根据map里面的key获取相应的值
            //使用json转换工具Gson
            Gson gson=new Gson();
            HashMap mapAccessToken = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) mapAccessToken.get("access_token");
            String openid = (String) mapAccessToken.get("openid");
            //拿着access_token和openid，再去请求微信提供的地址，获取扫描人的信息
            //访问微信的资源服务器，获取用户信息
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                    "?access_token=%s" +
                    "&openid=%s";
            //拼接两个参数
            String userInfoUrl = String.format(
                    baseUserInfoUrl,
                    access_token,
                    openid
            );
            //发送请求
            String userInfo = HttpClientUtils.get(userInfoUrl);
            //获取返回userInfo字符串扫描人信息
            HashMap userInfoMap = gson.fromJson(userInfo, HashMap.class);
            String nickname = (String)userInfoMap.get("nickname");//昵称
            String headimgurl = (String)userInfoMap.get("headimgurl");//头像
            //把扫描人信息添加到数据库，加入前要判断数据库是否有相同信息，有就不加

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:http://localhost:3000";
    }


}
