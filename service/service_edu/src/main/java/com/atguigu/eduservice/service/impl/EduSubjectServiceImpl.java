package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excl.Exclentity;
import com.atguigu.eduservice.lisenler.SubjectExcelListener;
import com.atguigu.eduservice.lisenler.SubjectLisener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-11-29
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
  @Autowired
  private  SubjectLisener subjectLisener;

    @Override
    public void saveSubject(MultipartFile multipartFile,EduSubjectService eduSubjectService) {


        try {
            //文件输入流
            InputStream inputStream=multipartFile.getInputStream();
            //调用方法进行读取
            EasyExcel.read(inputStream, Exclentity.class,subjectLisener).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
