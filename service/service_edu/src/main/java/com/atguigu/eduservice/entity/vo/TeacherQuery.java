package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



@Data
public class TeacherQuery {
    @ApiModelProperty(value = "教师名称，模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔1高级讲师，2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间",example = "2020-11-25 14:41:25")
    private String begin;//注意使用String类型，前端传过来的数据无需类型转换

    @ApiModelProperty(value = "查询结束时间",example = "2020-11-26 14:41:25")
    private String end;




}
