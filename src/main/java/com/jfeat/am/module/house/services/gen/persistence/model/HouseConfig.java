package com.jfeat.am.module.house.services.gen.persistence.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.models.auth.In;

import java.io.Serializable;

/**
 * @description: 匠城小程序可配置字段实体类
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/5/20 19:09
 * @author: hhhhhtao
 */
@TableName(value = "t_house_config")
public class HouseConfig extends Model<HouseConfig> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    // 字段分组名
    private String fieldGroupName;

    // 字段key
    private String fieldKey;

    // 字段名
    private String fieldName;

    // 字段值
    private String fieldValue;

    // 字段值的类型
    private String fieldValueType;

    // 该配置字段的状态
    private Integer status;

    // 字段说明
    private String explain;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFieldGroupName() {
        return fieldGroupName;
    }

    public void setFieldGroupName(String fieldGroupName) {
        this.fieldGroupName = fieldGroupName;
    }

    public String getFieldKey() {
        return fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getFieldValueType() {
        return fieldValueType;
    }

    public void setFieldValueType(String fieldValueType) {
        this.fieldValueType = fieldValueType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }
}
