package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.SysMenuentity;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.atguigu.eduservice.mapper.SysMenuMapper;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.eduservice.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-11-24
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenuentity> implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;


    @Override
    public List<SysMenuentity> getTreeMenu() {
        QueryWrapper<SysMenuentity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);

        List<SysMenuentity> root = sysMenuMapper.selectList(queryWrapper);
        List<SysMenuentity> all = sysMenuMapper.selectList(null);
        for (SysMenuentity sysMenuentity : root) {
            sysMenuentity.setChildren(getchildrens(sysMenuentity,all));

        }

        return root;
    }

    //递归查询子集合，返回树形数据
    public List<SysMenuentity> getchildrens(SysMenuentity sysMenuentity, List<SysMenuentity> all) {

        QueryWrapper<SysMenuentity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", sysMenuentity.getMenuId());

        List<SysMenuentity> childrenList2=new ArrayList<>();
        for (SysMenuentity menuentity:all){
                   if (menuentity.getParentId().equals(sysMenuentity.getMenuId())){
                       childrenList2= sysMenuMapper.selectList(queryWrapper);
                       sysMenuentity.setChildren(childrenList2);
                       for (SysMenuentity sysMenuentity1:childrenList2){
                           if (null!=sysMenuentity.getChildren()&&sysMenuentity.getChildren().size()>0){
                               getchildrens(sysMenuentity1,all);
                           }
                       }

                   }

               }

        return childrenList2;

    }

    @Override
    public void deleteTreeMenu(Long munId) {
        //创建list集合用于封装所有删除菜单的id
        List<Long> idList =new ArrayList<>();
        selectIdList(munId,idList);
        idList.add(munId);

        int i = sysMenuMapper.deleteBatchIds(idList);


    }

    private  void selectIdList(Long menu_id,List<Long> idList){
        //查询菜单里面的子菜单id
        QueryWrapper queryWrapper =new QueryWrapper();
        queryWrapper.eq("parent_id",menu_id);
        queryWrapper.select("menu_id");
        List<SysMenuentity> allidlist = sysMenuMapper.selectList(queryWrapper);
                  allidlist.stream().forEach(item->{
                      //封装到idList
                      idList.add(item.getMenuId());
                      //递归查询
                      this.selectIdList(item.getMenuId(),idList);
                  });

    }





}
