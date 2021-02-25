package com.atguigu.eduservice.controller;


import com.atguigu.common_utils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-24
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/edu-teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @ApiOperation(value = "查询所有")
    @Cacheable(key = "'treeMenul'",value = "treeMenuList")
    @GetMapping("/findAll")
    public R findAll() {
        List<EduTeacher> result = eduTeacherService.list(null);
        return R.ok().data("result", result);

    }

    @ApiOperation(value = "逻辑删除")
    @DeleteMapping("/{id}")
    public R removeTeacher(@PathVariable String id) {
        boolean result = eduTeacherService.removeById(id);
        if (result) {

            return R.ok();
        } else {
            return R.error();
        }
    }


    @ApiOperation(value = "普通分页查询")
    @GetMapping("/pageListTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable Long current, @PathVariable Long limit) {
        Page<EduTeacher> page = new Page<>(current, limit);
        IPage<EduTeacher> eduTeacherIPage = eduTeacherService.page(page, null);
        List<EduTeacher> records = eduTeacherIPage.getRecords();
        long total = eduTeacherIPage.getTotal();
        long current1 = eduTeacherIPage.getCurrent();
        long size = eduTeacherIPage.getSize();
        long pages = eduTeacherIPage.getPages();
        HashMap<String, Object> result = new HashMap<>();
        result.put("current", current1);
        result.put("size", size);
        result.put("pages", pages);
        result.put("total", total);
        result.put("records", records);
        return R.ok().data("result", result);
    }

    @ApiOperation(value = "带条件分页查询")
    @PostMapping("/pageListTeacher/{current}/{limit}")
    public R pageConditionListTeacher(@PathVariable Long current, @PathVariable Long limit,@RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建page对象
        Page<EduTeacher> teacherPage = new Page<>(current, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件值是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(level)) {
            wrapper.eq("level", level);
        }
        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }
        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_modified", end);
        }


        //调用方法实现条件查询分页
        eduTeacherService.page(teacherPage, wrapper);
        List<EduTeacher> records = teacherPage.getRecords();
        long total = teacherPage.getTotal();
        long current1 = teacherPage.getCurrent();
        long size = teacherPage.getSize();
        long pages = teacherPage.getPages();
        HashMap<String, Object> result = new HashMap<>();
        result.put("current", current1);
        result.put("size", size);
        result.put("pages", pages);
        result.put("total", total);
        result.put("records", records);
        return R.ok().data("result", result);

    }


    @ApiOperation(value = "添加讲师方法")
    @PostMapping("/addTeacher")
    public  R addTeacher(@RequestBody EduTeacher  eduTeacher){
        boolean result = eduTeacherService.save(eduTeacher);
        if (result){
            return R.ok();
        }else {
            return  R.error();
        }

    }

    @ApiOperation(value = "根据id查讲师")
    @GetMapping("/getTeacherById/{id}")
    public  R getTeacherById(@PathVariable String id){
        EduTeacher result = eduTeacherService.getById(id);
        return R.ok().data("result",result);
    }

    @ApiOperation(value = "修改讲师方法")
    @PostMapping("/updateTeacher")
    public  R updateTeacherById(@RequestBody(required = false) EduTeacher eduTeacher){
        boolean result = eduTeacherService.updateById(eduTeacher);
        if (result){
            return R.ok();
        }else {
            return  R.error();
        }
    }



}

