package com.jfeat.am.module.house.services.domain.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jfeat.am.module.house.services.gen.persistence.model.HouseConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description: 【匠城小程序】系统配置字段项mapper
 * @project: djhouse-api
 * @version: 1.0
 * @date: 2023/5/25 16:32
 * @author: hhhhhtao
 */
public interface HouseConfigMapper extends BaseMapper<HouseConfig> {

    List<HouseConfig> list(@Param("fieldGroupName") String fieldGroupName);

    HouseConfig getById(@Param("id") Integer id);

    int updateFieldValueById(@Param("id") Integer id, @Param("fieldValue") String fieldValue);

    String getFieldValueByFieldGroupNameAndFieldName(@Param("fieldGroupName") String fieldGroupName, @Param("fieldName") String fieldName);

    HouseConfig getByFieldGroupNameAndFieldName(@Param("fieldGroupName") String fieldGroupName, @Param("fieldName") String fieldName);
}
