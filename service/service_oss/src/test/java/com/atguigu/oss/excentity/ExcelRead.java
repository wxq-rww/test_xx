package com.atguigu.oss.excentity;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

public class ExcelRead extends AnalysisEventListener<Demodata> {
    @Override
    public void invoke(Demodata demodata, AnalysisContext analysisContext) {
        System.out.println(demodata);
    }
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头"+headMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
