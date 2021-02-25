package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.atguigu.oss.service.OssService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POIND;
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId =ConstantPropertiesUtils.ACCESS_KEY_ID ;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//获取文件名称
            String filename = file.getOriginalFilename();
            //在文件名称里面添加唯一随机值
            String uuid= UUID.randomUUID().toString().replaceAll("-","");
            filename=uuid+filename;
//            String datapath = new DateTime().toString("yyyy/MM/dd");
//            StringBuilder filename1=new StringBuilder();
//            filename1.append(datapath);
//            filename1.append("/");
//            filename1.append(filename);
//            filename1.append(uuid);
            //把文件按照日期分类
            //获取当前日期
            String datapath = new DateTime().toString("yyyy/MM/dd");
          //  拼接
            filename=datapath+"/"+filename;

// 创建PutObjectRequest对象。
            InputStream inputStream =file.getInputStream();
//第一个参数Bucket名称，第二个参数上传到oss文件路径和文件名称，第三个参数上传文件的输入流
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, filename, inputStream);
// 上传文件。
            ossClient.putObject(putObjectRequest);

// 关闭OSSClient。
            ossClient.shutdown();
          //把上传之后的路径返回
          //需要把上传到阿里云oss的路径手动拼接出来
            String url="https://"+bucketName+"."+endpoint+"/"+filename;
            return  url;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

// 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
// ObjectMetadata metadata = new ObjectMetadata();
// metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
// metadata.setObjectAcl(CannedAccessControlList.Private);
// putObjectRequest.setMetadata(metadata);



    }
}
