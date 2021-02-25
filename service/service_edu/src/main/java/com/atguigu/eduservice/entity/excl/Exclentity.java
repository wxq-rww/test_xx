package com.atguigu.eduservice.entity.excl;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class Exclentity {
    @ExcelProperty(index = 0)
    private String onesubject;

    @ExcelProperty(index = 1)
    private String twosubject;
}
