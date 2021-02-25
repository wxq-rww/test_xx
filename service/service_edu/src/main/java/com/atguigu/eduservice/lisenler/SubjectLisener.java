package com.atguigu.eduservice.lisenler;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excl.Exclentity;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectLisener extends AnalysisEventListener<Exclentity> {
    //因为SubjectLisener不能交给Spring进行管理，需要自己new，不能注入其他对象
    //不能实现数据库操作
    @Autowired
    private  EduSubjectService eduSubjectService;
//    public EduSubjectService eduSubjectService;
//
//    public SubjectLisener(EduSubjectService eduSubjectService) {
//        this.eduSubjectService = eduSubjectService;
//    }
//
//    public SubjectLisener() {
//    }

    //读取Excel中的数据，一行一行进行读取
    @Override
    public void invoke(Exclentity exclentity, AnalysisContext analysisContext) {
//        if (null == exclentity) {
//            throw new RuntimeException("文件数据为空");
//        }
        //一行一行读取，每次读取有两个值，第一个值一级分类，第二个值二级分类
        //先判断一级分类是否重复
        EduSubject existonSubject = this.existonSubject(eduSubjectService, exclentity.getOnesubject());
        if (null == existonSubject) {
            existonSubject = new EduSubject();
            existonSubject.setTitle(exclentity.getOnesubject());
            existonSubject.setParentId("0");
            eduSubjectService.save(existonSubject);

        }
        //获取一级分类的id值
        String pid = existonSubject.getId();
        //添加二级分类
        //判断二级分类是否重复
        EduSubject existtwoSubject = this.existtwoSubject(eduSubjectService, exclentity.getTwosubject(), pid);
        if (null == existtwoSubject) {
            existtwoSubject = new EduSubject();
            existtwoSubject.setTitle(exclentity.getTwosubject());
            existtwoSubject.setParentId(pid);
            eduSubjectService.save(existtwoSubject);

        }


    }

    //判断一级分类不能重复添加
    private EduSubject existonSubject(EduSubjectService eduSubjectService, String name) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id", "0");
        EduSubject oneSubject = eduSubjectService.getOne(queryWrapper);
        return oneSubject;
    }

    //判断二级分类不能重复添加
    private EduSubject existtwoSubject(EduSubjectService eduSubjectService, String name, String pid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("title", name);
        queryWrapper.eq("parent_id", pid);
        EduSubject twoSubject = eduSubjectService.getOne(queryWrapper);
        return twoSubject;
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
