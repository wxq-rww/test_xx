package com.atguigu.oss.excentity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Demodata {
    //设置excl表头
    @ExcelProperty(value = "学生编号",index = 0)
    private Integer sno;
    @ExcelProperty(value = "学生姓名",index = 1)
    private  String name;

}
