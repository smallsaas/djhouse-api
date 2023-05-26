package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 匠城小程序可配置字段的分组实体类
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/5/20 18:41
 * @author: hhhhhtao
 */
@TableName(value = "t_house_config_group")
public class HouseConfigGroup extends Model<HouseConfigGroup> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    // 分组键
    private String groupKey;

    // 分组名
    private String groupName;

    // 分组说明
    private String explain;

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
