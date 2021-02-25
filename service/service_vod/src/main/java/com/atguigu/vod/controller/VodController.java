package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.atguigu.common_utils.R;
import com.atguigu.vod.Utils.ConstantVodUtils;
import com.atguigu.vod.Utils.InitObject2;
import com.atguigu.vod.clicent.OssClicent;
import com.atguigu.vod.service.VodService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduvod/video")
@CrossOrigin
public class VodController {

    @Autowired
    private VodService vodService;
    @Autowired
    private OssClicent ossClicent;

    //上传视频到阿里云
    @ApiOperation(value = "上传视频到阿里云")
    @PostMapping("uploadAlyiVideo")
    public R uploadAlyiVideo(MultipartFile file) {
        //返回上传视频id
        String videoId = vodService.upLoadVideo(file);
        return R.ok().data("videoId",videoId);
    }


    //删除上传到阿里云的视频
    /**
     * 删除视频
     * @param //client1 发送请求客户端
     * @return DeleteVideoResponse 删除视频响应数据
     * @throws Exception
     */
    @ApiOperation(value = "删除上传到阿里云的视频")
    @DeleteMapping ("deletAlyiVideo/{id}")
    public  R deleteVideo(@PathVariable(value = "id") String id) throws Exception {


        DeleteVideoRequest request = null;
        DefaultAcsClient client1=null;
        try {
            client1 = InitObject2.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            request = new DeleteVideoRequest();
            //支持传入多个视频ID，多个用逗号分隔
            request.setVideoIds(id);
            DeleteVideoResponse result = client1.getAcsResponse(request);
            return R.ok().data("result",result);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }

    }
    @ApiOperation(value = "Fign远程调用测试")
    @PostMapping(value = "/testClient",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public R testClient(@RequestPart("file") MultipartFile file) {
        R url = ossClicent.uploadOssFile(file);
        return R.ok().data("url",url);
    }






}
