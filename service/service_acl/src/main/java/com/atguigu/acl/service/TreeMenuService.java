package com.atguigu.acl.service;

import com.atguigu.acl.entity.TreeMenuEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface TreeMenuService extends IService<TreeMenuEntity> {

   public List<TreeMenuEntity> getTreeMuenList() ;
   public List<TreeMenuEntity> getTreeMuenList2() ;
}
