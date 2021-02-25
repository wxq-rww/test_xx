package com.atguigu.acl.mapper;

import com.atguigu.acl.entity.TreeMenuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TreeMenuMapper extends BaseMapper<TreeMenuEntity> {
    public List<TreeMenuEntity> selectListsx(String id);
}
