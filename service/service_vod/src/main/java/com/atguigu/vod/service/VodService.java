package com.atguigu.vod.service;


import org.springframework.web.multipart.MultipartFile;


public interface VodService {
  public   String upLoadVideo(MultipartFile multipartFile);
}
