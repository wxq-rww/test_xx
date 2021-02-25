package com.atguigu.oss;

import com.alibaba.excel.EasyExcel;
import com.atguigu.oss.excentity.Demodata;
import com.atguigu.oss.excentity.ExcelRead;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ExeclTest {
    @Test
    public void name() {
        //1.设置写入文件夹地址和excl文件名称
        String filenname="C:\\Users\\RWW84\\Desktop\\工作簿1.xlsx";
        //2.调用easyexcel里面的方法实现写操作
        EasyExcel.write(filenname, Demodata.class).sheet("学生列表").doWrite(getData());

    }

//创建方法返回List集合
    private  static List<Demodata> getData(){
        List<Demodata> demodata= new ArrayList<>();
        for (int i = 0; i <5 ; i++) {
            Demodata demo=new Demodata();
            demo.setSno(i);
            demo.setName("wxq"+i);
            demodata.add(demo);
        }
        return demodata;
    }

    @Test
    public void read() {
        String filenname="C:\\Users\\RWW84\\Desktop\\工作簿1.xlsx";
        EasyExcel.read(filenname,Demodata.class,new ExcelRead()).sheet().doRead();
    }



}
