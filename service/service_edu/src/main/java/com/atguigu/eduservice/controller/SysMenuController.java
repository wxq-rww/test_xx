package com.atguigu.eduservice.controller;

import com.atguigu.common_utils.R;
import com.atguigu.eduservice.entity.SysMenuentity;
import com.atguigu.eduservice.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "返回树形列表")
@RestController
@RequestMapping("/sys")

public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation(value = "返回树形结构")
    @GetMapping("/treeMenu")
    public R  treeMenu() {
        List<SysMenuentity> treeMenu =  sysMenuService.getTreeMenu();
        return R.ok().data("data",treeMenu);
    }

      @ApiOperation(value = "删除树形结构")
      @PostMapping("/deletTree/{munId}")
     public R name(@PathVariable(value = "munId") Long munId) {
       sysMenuService.deleteTreeMenu(munId);

        return R.ok();
          }



}
