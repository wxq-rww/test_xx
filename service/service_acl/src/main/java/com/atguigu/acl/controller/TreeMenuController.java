package com.atguigu.acl.controller;

import com.atguigu.acl.entity.TreeMenuEntity;
import com.atguigu.acl.service.TreeMenuService;
import com.atguigu.common_utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(description = "返回树形菜单")
@CrossOrigin
@RestController
@RequestMapping("/TreeMenu")
public class TreeMenuController {
    @Autowired
    private TreeMenuService treeMenuService;



    @ApiOperation(value = "查询树形菜单")
    @GetMapping("/treeList")
    public R treeList() {
    List<TreeMenuEntity> treeMenuEntities = treeMenuService.getTreeMuenList();

       return R.ok().data("result",treeMenuEntities);
  }

    @ApiOperation(value = "查询树形菜单2")
   // @Cacheable(key = "'treeMenu'",value = "treeMenuList")//加入到redis缓存
    @GetMapping("/treeListl")
    public R treeList2() {
        List<TreeMenuEntity> treeMenuEntities = treeMenuService.getTreeMuenList2();

        return R.ok().data("result",treeMenuEntities);
    }


}
