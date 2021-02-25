package com.atguigu.eduservice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@Data
@TableName("sys_menu")
public class SysMenuentity {
    @TableId
    private Long menuId;
    private Long parentId;
    private  String name;
    private  String url;
    private String perms;
    private Integer type;
    private String icon;
    private  Integer orderNum;
    @TableField(exist = false)
    private List<SysMenuentity> children;

}
