package com.jfeat.am.module.house.services.domain.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseConfig;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 【匠城小程序】系统配置分组DTO
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/5/25 23:42
 * @author: hhhhhtao
 */
public class HouseConfigGroupDTO {

    private static final long serialVersionUID = 1L;

    // 分组组件
    private Integer id;

    // 分组键
    private String groupKey;

    // 分组名
    private String groupName;

    // 分组说明
    private String explain;

    // 配置字段列表，子表（t_house_config）
    List<HouseConfig> houseConfigs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
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

    public List<HouseConfig> getHouseConfigs() {
        return houseConfigs;
    }

    public void setHouseConfigs(List<HouseConfig> houseConfigs) {
        this.houseConfigs = houseConfigs;
    }
}
