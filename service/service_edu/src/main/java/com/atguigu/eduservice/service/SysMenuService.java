package com.atguigu.eduservice.service;

import com.atguigu.common_utils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.SysMenuentity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-11-24
 */
public interface SysMenuService extends IService<SysMenuentity> {

   public List<SysMenuentity> getTreeMenu();

    public  void deleteTreeMenu(Long munId);
}
