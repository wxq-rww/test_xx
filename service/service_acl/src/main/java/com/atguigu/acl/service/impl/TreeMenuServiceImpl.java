package com.atguigu.acl.service.impl;

import com.atguigu.acl.entity.TreeMenuEntity;
import com.atguigu.acl.mapper.TreeMenuMapper;
import com.atguigu.acl.service.TreeMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TreeMenuServiceImpl extends ServiceImpl<TreeMenuMapper, TreeMenuEntity> implements TreeMenuService {
      @Autowired
      private TreeMenuMapper treeMenuMapper;


    @Override
    public List<TreeMenuEntity> getTreeMuenList() {
        List<TreeMenuEntity> all = treeMenuMapper.selectList(null);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("pid","0");
        List<TreeMenuEntity> root = treeMenuMapper.selectList(queryWrapper);
       for (TreeMenuEntity rootmenuList:root){
           rootmenuList.setChildren( getchildrens( rootmenuList,all));
       }

        return root;
    }

    //递归查询子集合，返回树形数据
    public List<TreeMenuEntity> getchildrens(TreeMenuEntity sysMenuentity, List<TreeMenuEntity> all) {

        QueryWrapper<TreeMenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", sysMenuentity.getId());

        List<TreeMenuEntity> childrenList2=new ArrayList<>();
        childrenList2= treeMenuMapper.selectList(queryWrapper);

//        for (TreeMenuEntity menuentity:all){
//            if (menuentity.getPid().equals(sysMenuentity.getId())){
                sysMenuentity.setChildren(childrenList2);
                for (TreeMenuEntity sysMenuentity1:childrenList2){

                   // if (null!=sysMenuentity.getChildren()&&sysMenuentity.getChildren().size()>0){
                        getchildrens(sysMenuentity1,all);
//                    }
                }

//            }
//
//        }

        return childrenList2;

    }

//下方是方法二
    @Override
    public List<TreeMenuEntity> getTreeMuenList2() {
        List<TreeMenuEntity> all = treeMenuMapper.selectList(null);
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("pid","0");
        List<TreeMenuEntity> root = treeMenuMapper.selectList(queryWrapper);
        for (TreeMenuEntity rootmenuList:root){
            rootmenuList.setChildren( getchildrens( rootmenuList,all));
        }

        return root;
    }

    //递归
    public List<TreeMenuEntity> getchildrens2(TreeMenuEntity sysMenuentity, List<TreeMenuEntity> all){
        QueryWrapper<TreeMenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", sysMenuentity.getId());

        List<TreeMenuEntity> childrenList2=new ArrayList<>();
        childrenList2= treeMenuMapper.selectList(queryWrapper);
        List<TreeMenuEntity> finalChildrenList = childrenList2;
        childrenList2.stream().forEach(
                item->{
                     finalChildrenList.add((TreeMenuEntity) item.getChildren());
                     this.getchildrens2((TreeMenuEntity) item.getChildren(),all);
                }
        );
        return childrenList2;

    }




}
